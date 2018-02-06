package root.onesms.Connect

import retrofit2.*

/**
 * Created by root1 on 2017. 10. 31..
 */
interface ResCall<T>: Callback<T> {

    override fun onResponse(call: Call<T>?, response: Response<T>) = onCallBack(response.code(), response.body())

    fun onCallBack(code: Int, body: T?)

    override fun onFailure(call: Call<T>?, t: Throwable) = t.printStackTrace()
}