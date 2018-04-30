package root.onesms.Connect

import com.google.gson.*
import retrofit2.*
import retrofit2.http.*

/**
 * Created by root1 on 2017. 10. 12..
 */
interface Api {

    @POST("urlshortener/v1/url")
    fun getShortUrl(@Body body: LongUrlModel, @Query("key")key: String) : Call<JsonObject>

}

data class LongUrlModel(val longUrl: String)