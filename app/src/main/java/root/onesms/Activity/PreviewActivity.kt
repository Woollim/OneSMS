package root.onesms.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.view_lockscreen.view.*
import root.onesms.R
import root.onesms.Util.BaseActivity

/**
 * Created by root1 on 2017. 10. 31..
 */
class PreviewActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val view = LayoutInflater.from(this).inflate(R.layout.view_lockscreen, null)
        val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        )

        rootLayout.addView(view, param)

        toolBar.bringToFront()

        backButton.setOnClickListener {
            finish()
        }

        with(view){
            contactText.text = getPreference().getString("${R.string.info_contact}", "여기에 연락처가 표시됩니다.")
        }

    }

}