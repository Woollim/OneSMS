package root.onesms.Manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by root1 on 2017. 10. 20..
 */
class LockScreenReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
       when(p1?.action){
           Intent.ACTION_BOOT_COMPLETED -> print("hello world")
           "android.provider.Telephony.SMS_RECEIVER" -> print("nice to meet you")
       }
    }

    fun createLockScreen(){

    }


}