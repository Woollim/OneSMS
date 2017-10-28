package root.onesms.Manager

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.telephony.SmsManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import root.onesms.Connect.Connect
import root.onesms.R

/**
 * Created by root1 on 2017. 10. 12..
 */
public class SendSMSManager(context : Context, contact : String){

    var shortUrlStr = ""
    var locationUrlStr = ""

    init {
        shortUrlStr = context.getString(R.string.short_url)
        locationUrlStr = context.getString(R.string.locate_url)
        getLocation(context, contact)
    }


    @SuppressLint("MissingPermission")
    private fun getLocation(context: Context, contact : String){
        val locationProviderClient = FusedLocationProviderClient(context)
        locationProviderClient.lastLocation.addOnCompleteListener {
            task ->
            android.os.Handler().post({
                if(task.isSuccessful){
                    Log.d("location", task.result.toString())
                    sendSMS(contact, task.result)
                }else{
                    Log.d("location", task.exception.toString())
                    sendSMS(contact, null)
                }
            })
        }
    }

    private fun sendSMS(contact: String, location : Location?){
        val smsManager = SmsManager.getDefault()
        fun send(text : String){
            smsManager.sendTextMessage(contact,null, text, null, null)
        }
        send("OneSMS의 위치정보 문자입니다.")
        location?.let {
            getShortUrl(location, {text -> send(text)})
            send("위도 : "+location.latitude)
            send("경도 : "+location.longitude)
            return
        }

        send("위치정보를 불러오지 못했습니다.")
    }

    private fun getShortUrl(location: Location, func: (String) -> Unit){
        Connect.getApi()?.getShortUrl(shortUrlStr + location.latitude + "," + location.longitude)
            ?.enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                if (response?.code() == 200){
                    val shortUrl = response.body()?.get("shortUrl")?.asString
                    func(shortUrl!!)
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

}
