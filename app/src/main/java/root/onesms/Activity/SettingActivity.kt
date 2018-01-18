package root.onesms.Activity

import android.*
import android.content.*
import android.os.*
import android.support.v7.widget.*
import android.text.method.*
import android.util.*
import android.view.*
import com.github.ajalt.reprint.core.*
import com.gun0912.tedpermission.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.view_header.view.*
import kotlinx.android.synthetic.main.view_infomation.view.*
import kotlinx.android.synthetic.main.view_switch.view.*
import root.onesms.R
import root.onesms.Util.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var contentArray = arrayListOf(R.string.header_option, R.string.option_start, R.string.header_info, R.string.info_message, R.string.info_open, R.string.info_contact)

        recycler_setting.layoutManager = LinearLayoutManager(this)
        recycler_setting.adapter = SettingAdapter(contentArray)

        TedPermission
                .with(this)
                .setPermissions(android.Manifest.permission.RECEIVE_SMS,android.Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .setPermissionListener(object : PermissionListener{
                    override fun onPermissionGranted() {}

                    override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                        showToast("권한을 주지 않으면 서비스를 실행할 수 없습니다.")
                        finish()
                    }

                }).check()


        button_setting_edit.setOnClickListener { startActivity(Intent(this, EditActivity::class.java)) }

    }

    override fun onStart() {
        super.onStart()
        recycler_setting.adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menu_preview -> {
                startActivity(Intent(this, PreviewActivity::class.java))
                true
            }
            R.id.menu_guide -> {
                startActivity(Intent(this, InfoActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class SettingAdapter(contentArray : ArrayList<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        var contentArray : ArrayList<Int>? = null
        var pref : SharedPreferences? = null

        init {
            this.contentArray = contentArray
            pref = getPreference()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder?{
            val title = getString(contentArray!![viewType])
            var viewNum : Int

            val view = LayoutInflater.from(parent.context).inflate(when{
                title.length == 2 -> {
                    viewNum = 1
                    R.layout.view_header
                }
                title.equals("서비스 실행") || title.equals("지문 잠금 활성화") -> {
                    viewNum = 2
                    R.layout.view_switch
                }
                else -> {
                    viewNum = 3
                    R.layout.view_infomation
                }
            }, parent, false)

            return when(viewNum){
                1 -> HeaderViewHolder(view)
                2 -> SwitchViewHolder(view)
                else -> ContentViewHolder(view)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onBindViewHolder(hl: RecyclerView.ViewHolder, position: Int) {
            val id = contentArray!!.get(position)
            val title = getString(id)
            Log.d("xxx", "$id")
            when{
                title.length == 2 -> {
                    val holder = hl as HeaderViewHolder
                    holder.bindView(title)
                }
                title.equals("서비스 실행") -> {
                    val holder = hl as SwitchViewHolder
                    holder.bindView(title!!, pref!!.getBoolean("$id", false), id)
                }
                else -> {
                    val holder = hl as ContentViewHolder
                    holder.bindView(title!!, pref!!.getString("$id", "아직 등록되지 않았습니다."))
                }
            }
        }

        override fun getItemCount(): Int = contentArray?.size ?: 0

        inner class ContentViewHolder(view : View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String, content: String) {
                with(itemView){
                    titleText.text = title
                    if(title.equals(getString(R.string.info_open))){
                        contentText.transformationMethod = PasswordTransformationMethod()
                    }
                    contentText.text = content
                }
            }

        }

        inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String){
                with(itemView){
                    headerText.text = title
                }
            }

        }

        inner class SwitchViewHolder(view: View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String, set: Boolean, id : Int){
                with(itemView){
                    infoText.text = title
                    setSwitch.isChecked = set
                    setSwitch.setOnCheckedChangeListener { _, checked ->
                        if(title.equals(getString(R.string.option_start)) && pref!!.all.size < 2){
                            setSwitch.isChecked = false
                            showToast("값을 입력하여야 OneSMS를 실행할 수 있습니다.")
                        }else {
                            val editor = pref!!.edit()
                            editor.remove("$id")
                            editor.putBoolean("$id", checked)
                            editor.apply()
                        }
                    }
                }
            }

        }
    }

}
