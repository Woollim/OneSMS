package root.onesms.Connect

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by root1 on 2017. 10. 12..
 */
object Connect {

    lateinit var api: Api

    init {
        val cilent = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(cilent).build())
                .baseUrl("https://www.googleapis.com/")
                .build()
        api = retrofit.create(Api::class.java)
    }


}