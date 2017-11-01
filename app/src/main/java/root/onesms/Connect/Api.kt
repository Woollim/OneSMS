package root.onesms.Connect

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by root1 on 2017. 10. 12..
 */
interface Api {

    @GET("Api/create.php")
    fun getShortUrl(@Query("longUrl")url: String) : Call<JsonObject>

}