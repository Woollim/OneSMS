package root.onesms.Service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationListener
import com.github.ajalt.reprint.core.Reprint
import org.jetbrains.annotations.Nullable

/**
 * Created by root1 on 2017. 10. 20..
 */
class LockScreenService : Service() {

    @Nullable
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this)
        craateLockScreen()
    }

    private fun craateLockScreen(){
        val windowManager = getSystemService(Context.LAYOUT_INFLATER_SERVICE)!! as WindowManager
        val param = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        )
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE)!! as LayoutInflater
//        val lockView = inflater.inflate()

    }

    private fun startfingerPrint(){
        Reprint.authenticate(object : AuthenticationListener{
            override fun onSuccess(moduleTag: Int) {

            }

            override fun onFailure(failureReason: AuthenticationFailureReason?, fatal: Boolean, errorMessage: CharSequence?, moduleTag: Int, errorCode: Int) {

            }
        })
    }

}