package root.onesms.Manager

import android.annotation.*
import android.content.*
import android.location.*
import android.telephony.*
import com.google.android.gms.location.*
import com.google.gson.*
import root.onesms.Connect.*
import root.onesms.R

/**
 * Created by root1 on 2017. 10. 12..
 */
public class SendSMSManager(val context : Context, val contact : String){

    var locationUrlStr = ""

    init {
        locationUrlStr = context.getString(R.string.locate_url)
        getLocation()
    }


    @SuppressLint("MissingPermission")
    private fun getLocation(){
        val locationProviderClient = FusedLocationProviderClient(context)
        locationProviderClient.lastLocation.addOnCompleteListener {
            task ->
            android.os.Handler().post({
                if(task.isSuccessful){
                    sendSMS(task.result)
                }else{
                    sendSMS(null)
                }
            })
        }
    }

    private fun sendSMS(location : Location?){
        val smsManager = SmsManager.getDefault()
        fun send(text : String){
            smsManager.sendTextMessage(contact,null, text, null, null)
        }
        send("OneSMS가 제공하는 휴대폰 위치 정보입니다.")
        location?.let {
            getShortUrl(it, {text -> send(text)})
            return
        }

        send("위치정보를 불러오지 못했습니다.")
    }

    private fun getShortUrl(location: Location, func: (String) -> Unit){
        Connect.api.getShortUrl(LongUrlModel(locationUrlStr + location.latitude + "," + location.longitude), context.getString(R.string.key_short_url))
                .enqueue(object : ResCall<JsonObject> {
                    override fun onCallBack(code: Int, body: JsonObject?) {
                        when(code){
                            200 -> {
                                body?.get("id")?.asString?.let { func(it) }
                            }
                            else -> {}
                        }
                    }
                })

    }

}
