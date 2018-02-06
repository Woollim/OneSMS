package root.onesms.Activity

import android.os.*
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.view_lockscreen.view.*
import root.onesms.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 10. 31..
 */
class PreviewActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val view = LayoutInflater.from(this).inflate(R.layout.view_lockscreen, null).apply{
            contactText.text = getPreference().getString("${R.string.info_contact}", "여기에 연락처가 표시됩니다.")
        }
        RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        ).let { layout_preview_root.addView(view, it) }

        tool_preview.bringToFront()

        button_preview_back.setOnClickListener { finish() }

    }

}