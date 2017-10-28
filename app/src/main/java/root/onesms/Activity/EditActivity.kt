package root.onesms.Activity

import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit.*
import root.onesms.R
import root.onesms.Util.BaseActivity

/**
 * Created by root1 on 2017. 10. 23..
 */
class EditActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            if (messageText.text.isEmpty() || openText.text.isEmpty() || contactText.text.isEmpty()){
                showToast("필수 항목들을 입력하세요")
            }else{
                saveData(R.string.info_message, messageText)
                saveData(R.string.info_open, openText)
                saveData(R.string.info_contact, contactText)
                finish()
            }
        }



    }

    fun saveData(id : Int, valueEdit : EditText){
        val editor = getPreference().edit()
        editor.remove("$id")
        editor.putString("$id", valueEdit.text.toString())
        editor.commit()
    }

}