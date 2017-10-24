package root.onesms.Util

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

/**
 * Created by root1 on 2017. 10. 12..
 */
object UtilClass {

    fun showToast(context : Context, msg : String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    public fun getPreference(context: Context) : SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

}