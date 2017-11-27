package root.onesms.Manager

import android.content.*
import android.graphics.*
import android.view.*
import com.github.ajalt.reprint.core.*
import kotlinx.android.synthetic.main.view_lockscreen.view.*
import kotlinx.android.synthetic.main.view_unlockscreen.view.*
import root.onesms.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 10. 26..
 */
class LockScreenManager(context: Context, soundManager: SoundManager) {

    lateinit var windowManager: WindowManager
    lateinit var inflator: LayoutInflater
    lateinit var soundManager: SoundManager
    lateinit var pref: SharedPreferences

    val param: WindowManager.LayoutParams = WindowManager.LayoutParams (
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT )

    var lockScreen: View? = null
    var unLockScreen: View? = null

    init {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE)!! as WindowManager
        inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!! as LayoutInflater
        pref = UtilClass.getPreference(context)
        this.soundManager = soundManager

        createLockScreen()

        Reprint.initialize(context)
        Reprint.authenticate(object : AuthenticationListener{
            override fun onSuccess(moduleTag: Int) {
                unLock()
            }

            override fun onFailure(failureReason: AuthenticationFailureReason?, fatal: Boolean, errorMessage: CharSequence?, moduleTag: Int, errorCode: Int) {

            }
        })

    }

    private fun createUnLockScreen(){
        unLockScreen = inflator.inflate(R.layout.view_unlockscreen, null)
        windowManager.addView(unLockScreen!!, param)

        with(unLockScreen!!){
            cancelButton.setOnClickListener {
                windowManager.removeView(unLockScreen)
                unLockScreen = null
            }
            checkButton.setOnClickListener {
                if (passwordEdit.text.toString() == pref.getString("${R.string.info_open}", "")){
                    unLock()
                }else{ passwordEdit.setText("") }
            }
        }
    }

    private fun createLockScreen(){
        lockScreen = inflator.inflate(R.layout.view_lockscreen, null)
        windowManager.addView(lockScreen!!, param)

        val editor = pref.edit()
        editor.putBoolean("${R.string.key_lock_state}", true)
        editor.commit()

        with(lockScreen!!){
            contactText.text = pref.getString("${R.string.info_contact}", "")
            unlockButton.setOnClickListener { createUnLockScreen() }
        }

    }

    private fun unLock(){
        unLockScreen?.let { windowManager.removeView(unLockScreen!!) }
        lockScreen?.let { windowManager.removeView(lockScreen!!) }
        soundManager.stopSound()
        val editor = pref.edit()
        editor.putBoolean("${R.string.key_lock_state}", false)
        editor.commit()
    }

}