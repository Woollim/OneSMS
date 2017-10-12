package root.onesms.Connect

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by root1 on 2017. 10. 12..
 */
object Connect {

    public fun getApi() : Api{
        val cilent = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(cilent).build())
                .build()

        return retrofit.create(Api::class.java)
    }


}