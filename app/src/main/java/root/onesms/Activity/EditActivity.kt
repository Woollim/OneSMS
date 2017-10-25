package root.onesms.Activity

import android.os.Bundle
import android.widget.EditText
import root.onesms.R
import root.onesms.Util.BaseActivity

/**
 * Created by root1 on 2017. 10. 23..
 */
class EditActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)



//        saveButton.setOnClickListener {
//            if(emptyCheck()){
//                showToast("값을 다 입력하세요")
//            }else{
//                saveData(R.string.info_message, messageText)
//                saveData(R.string.info_open, openText)
//                saveData(R.string.info_contact, contactText)
//                saveData(R.string.info_more, moreInfoText)
//
//                showToast("저장 되었습니다.")
//                finish()
//            }
//        }

    }

//    fun emptyCheck() : Boolean{
//        fun empty(e : EditText) : Boolean{
//            return e.text.toString().isEmpty()
//        }
//
//        return empty(messageText) || empty(openText) || empty(contactText) || empty(moreInfoText)
//    }

    fun saveData(id : Int, valueEdit : EditText){
        val editor = getPreference().edit()
        editor.remove("$id")
        editor.putString("$id", valueEdit.text.toString())
        editor.commit()
    }

}