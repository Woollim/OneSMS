package root.onesms.Activity

import android.content.Intent
import android.os.Bundle
import root.onesms.Util.BaseActivity

/**
 * Created by root1 on 2017. 11. 25..
 */
class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

}