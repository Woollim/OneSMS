package root.onesms.Util

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by root1 on 2017. 10. 12..
 */
open class BaseActivity : AppCompatActivity(){

    fun showToast(msg : String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    public fun getPreference() : SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

}