package root.onesms.Connect

import com.google.gson.JsonObject
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by root1 on 2017. 10. 12..
 */
interface Api {

    @POST("urlshortener/v1/url")
    fun getShortUrl(@Body body: LongUrlModel, @Query("key")key: String) : Call<JsonObject>

}

data class LongUrlModel(var longUrl: String)