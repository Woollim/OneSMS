package root.onesms.Util

import android.content.*
import android.support.v7.app.*
import android.widget.*

/**
 * Created by root1 on 2017. 10. 12..
 */
open class BaseActivity : AppCompatActivity(){

    fun showToast(msg : String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    fun getPreference(): SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

}