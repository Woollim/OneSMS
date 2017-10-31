package root.onesms.Manager

import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationListener
import com.github.ajalt.reprint.core.Reprint
import root.onesms.R

/**
 * Created by root1 on 2017. 10. 26..
 */
class LockScreenManager(context: Context, soundManager: SoundManager) {
    lateinit var windowManager: WindowManager
    lateinit var inflator: LayoutInflater
    lateinit var view: View
    lateinit var soundManager: SoundManager
    lateinit var param: WindowManager.LayoutParams

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

        lock()

        Reprint.initialize(context)

        Reprint.authenticate(object : AuthenticationListener{
            override fun onSuccess(moduleTag: Int) {
                unLock()
            }

            override fun onFailure(failureReason: AuthenticationFailureReason?, fatal: Boolean, errorMessage: CharSequence?, moduleTag: Int, errorCode: Int) {

            }
        })

    }

    private fun unLock(){
        Log.d("xxx", view.toString())
        windowManager.removeView(view)
    }

    private fun lock(){
        Log.d("xxx", view.toString())
        windowManager.addView(view, param)
    }
}