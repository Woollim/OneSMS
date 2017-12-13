package root.onesms.Manager

import android.content.*
import android.telephony.*
import root.onesms.*
import root.onesms.Service.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 10. 20..
 */
class OneSMSReceiver : BroadcastReceiver() {

    var pref: SharedPreferences? = null
    var lockState = false

    override fun onReceive(context: Context, intent: Intent) {

        pref = UtilClass.getPreference(context)
        if (!pref?.getBoolean("${R.string.option_start}", false)!!){
            return
        }

        lockState = pref?.getBoolean("${R.string.key_lock_state}", false)!!

        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                if (lockState) {
                    context.startService(Intent(context, LockScreenService::class.java))
                }
            }
            "android.provider.Telephony.SMS_RECEIVED" -> {
                if(sendMessage(intent, context) && !lockState) {
                    context.startService(Intent(context, LockScreenService::class.java))
                }
            }
        }
    }


    private fun sendMessage(intent: Intent, context: Context): Boolean {

        val bundle = intent.extras
        val messages = bundle.get("pdus")!! as Array<Object>
        val smsMessages = arrayOfNulls<SmsMessage>(messages.size)

        for (i in messages.indices){
            smsMessages[i] = SmsMessage.createFromPdu(messages[i] as ByteArray)
        }

        val smsMessage = smsMessages[0]
        val contact = smsMessage?.originatingAddress
        val msgBody = smsMessage?.messageBody

        msgBody?.let {
            if(msgBody.equals(pref?.getString("${R.string.info_message}", ""))){
                SendSMSManager(context, contact!!)
                return true
            }
        }

        return false
    }


}