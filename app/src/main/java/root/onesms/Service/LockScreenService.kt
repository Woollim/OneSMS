package root.onesms.Service

import android.app.*
import android.content.*
import android.os.*
import org.jetbrains.annotations.*
import root.onesms.Manager.*

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
        LockScreenManager(this, SoundManager(this))
    }

}