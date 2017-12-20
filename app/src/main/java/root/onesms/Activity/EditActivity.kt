package root.onesms.Activity

import android.os.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_edit.*
import root.onesms.*
import root.onesms.Util.*

/**
 * Created by root1 on 2017. 10. 23..
 */
class EditActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        button_edit_back.setOnClickListener { finish() }

        button_edit_save.setOnClickListener {
            if (edit_edit_message.text.isEmpty() || edit_edit_open.text.isEmpty() || edit_edit_contact.text.isEmpty()){
                showToast("필수 항목들을 입력하세요")
            }else{
                saveData(R.string.info_message, edit_edit_message)
                saveData(R.string.info_open, edit_edit_open)
                saveData(R.string.info_contact, edit_edit_contact)
                finish()
            }
        }

    }

    fun saveData(id : Int, valueEdit : EditText){
        val editor = getPreference().edit()
        editor.remove("$id")
        editor.putString("$id", valueEdit.text.toString())
        editor.apply()
    }

}