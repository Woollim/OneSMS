package root.onesms.Util

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

/**
 * Created by root1 on 2017. 10. 12..
 */
object UtilClass {

    fun showToast(context : Context, msg : String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    fun saveData(context: Context, key : String, value : String) : Boolean{
        val pref = getPreference(context).edit()
        pref.remove(key)
        pref.putString(key, value)
        return pref.commit()
    }

    fun getData(context: Context, key : String) : String = getPreference(context).getString(key, "")

    private fun getPreference(context: Context) : SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

}