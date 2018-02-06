package root.onesms.Activity

import android.*
import android.content.*
import android.os.*
import android.support.v7.widget.*
import android.text.method.*
import android.view.*
import com.gun0912.tedpermission.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.view_header.view.*
import kotlinx.android.synthetic.main.view_infomation.view.*
import kotlinx.android.synthetic.main.view_switch.view.*
import root.onesms.R
import root.onesms.Util.*

class SettingActivity: BaseActivity() {

    lateinit var adapter: SettingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val contentArray = arrayListOf(R.string.header_option, R.string.option_start, R.string.header_info, R.string.info_message, R.string.info_open, R.string.info_contact)

        adapter = SettingAdapter(contentArray)

        recycler_setting.apply {
            layoutManager = LinearLayoutManager(this@SettingActivity)
            adapter = this@SettingActivity.adapter
        }

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
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
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

    inner class SettingAdapter(val contentArray : ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder?{
            val title = getString(contentArray[viewType])
            var viewNum: Int

            LayoutInflater.from(parent.context).inflate(when{
                title.length == 2 -> {
                    viewNum = 1
                    R.layout.view_header
                }
                viewType == 1 -> {
                    viewNum = 2
                    R.layout.view_switch
                }
                else -> {
                    viewNum = 3
                    R.layout.view_infomation
                }
            }, parent, false).let {
                return when(viewNum){
                    1 -> HeaderViewHolder(it)
                    2 -> SwitchViewHolder(it)
                    else -> ContentViewHolder(it)
                }
            }
        }

        override fun getItemViewType(position: Int): Int = position

        override fun onBindViewHolder(hl: RecyclerView.ViewHolder, position: Int) {
            val id = contentArray[position]
            val title = getString(id)

            when{
                title.length == 2 -> {
                    val holder = hl as HeaderViewHolder
                    holder.bindView(title)
                }
                title.equals("서비스 실행") -> {
                    val holder = hl as SwitchViewHolder
                    holder.bindView(title, getPreference().getBoolean("$id", false))
                }
                else -> {
                    val holder = hl as ContentViewHolder
                    holder.bindView(title, getPreference().getString("$id", "아직 등록되지 않았습니다."))

                }
            }
        }

        override fun getItemCount(): Int = contentArray.size

        inner class ContentViewHolder(val view : View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String, content: String) {
                with(view){
                    titleText.text = title
                    contentText.text = content
                    if(title.equals(getString(R.string.info_open))) { contentText.transformationMethod = PasswordTransformationMethod() }

                }
            }

        }

        inner class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String) = with(itemView){ headerText.text = title }

        }

        inner class SwitchViewHolder(view: View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String, set: Boolean){
                with(itemView){
                    infoText.text = title
                    setSwitch.isChecked = set
                    setSwitch.setOnCheckedChangeListener { _, checked ->
                        if(getPreference().all.size < 2 && checked){
                            showToast("설정 값을 모두 입력해야 동작합니다.")
                            setSwitch.isChecked = false
                        }else{
                            getPreference().edit().run {
                                putBoolean("${R.string.option_start}", checked)
                                apply()
                            }
                        }}
                }
            }

        }
    }

}
