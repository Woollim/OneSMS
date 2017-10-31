package root.onesms.Manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.telephony.SmsMessage
import root.onesms.R
import root.onesms.Util.UtilClass

/**
 * Created by root1 on 2017. 10. 20..
 */
class OneSMSReceiver : BroadcastReceiver() {

    val PREFLOCKID = "isLock"
    var pref: SharedPreferences? = null
    var isLock = false

    override fun onReceive(context: Context, intent: Intent) {
        pref = UtilClass.getPreference(context)
        if (!pref?.getBoolean("${R.string.option_start}", false)!!)
            return

        isLock = pref?.getBoolean(PREFLOCKID, false)!!

        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                if (isLock) LockScreenManager(context, SoundManager(context))
            }
            "android.provider.Telephony.SMS_RECEIVED" -> {
                sendMessage(intent.extras.get("pdus") as Array<Any>, context)
                if(!isLock) LockScreenManager(context, SoundManager(context))
            }
        }
    }


    private fun sendMessage(p1: Array<Any>, context: Context) {
        val message = arrayOfNulls<SmsMessage>(p1.size)[0]
        if (message!!.messageBody.equals(pref?.getString("${R.string.option_start}", ""))) {
            SendSMSManager(context, message.messageBody)
        }
    }


}