package root.onesms.Activity

import android.content.*
import android.os.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 11. 25..
 */
class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, SettingActivity::class.java).let {
            startActivity(it)
            finish()
        }

    }

}