package root.onesms.Manager

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationListener
import com.github.ajalt.reprint.core.Reprint
import kotlinx.android.synthetic.main.view_lockscreen.view.*
import root.onesms.R
import root.onesms.Util.UtilClass

/**
 * Created by root1 on 2017. 10. 26..
 */
class LockScreenManager(context: Context, soundManager: SoundManager) {
    lateinit var windowManager: WindowManager
    lateinit var inflator: LayoutInflater
    lateinit var view: View
    lateinit var soundManager: SoundManager
    lateinit var param: WindowManager.LayoutParams

    var pref: SharedPreferences? = null

    init {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE)!! as WindowManager
        inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!! as LayoutInflater
        view = inflator.inflate(R.layout.view_lockscreen, null)
        param = WindowManager.LayoutParams (
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT )
        this.soundManager = soundManager
        pref = UtilClass.getPreference(context)

        lock()

        Reprint.initialize(context)

        Reprint.authenticate(object : AuthenticationListener{
            override fun onSuccess(moduleTag: Int) {
                unLock()
            }

            override fun onFailure(failureReason: AuthenticationFailureReason?, fatal: Boolean, errorMessage: CharSequence?, moduleTag: Int, errorCode: Int) {

            }
        })

        with(view){
            contactText.text = pref?.getString("${R.string.info_contact}", "")
            unlockButton.setOnClickListener {
                unLock()
            }
        }

    }

    private fun unLock(){
        soundManager.stopSound()
        windowManager.removeView(view)

        val editor = pref?.edit()
        editor?.remove("${R.string.key_isLock}")
        editor?.putBoolean("${R.string.key_isLock}", false)
        editor?.commit()

    }

    private fun lock(){
        windowManager.addView(view, param)

        val editor = pref?.edit()
        editor?.remove("${R.string.key_isLock}")
        editor?.putBoolean("${R.string.key_isLock}", true)
        editor?.commit()
    }

}