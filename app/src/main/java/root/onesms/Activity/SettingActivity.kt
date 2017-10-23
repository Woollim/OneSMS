package root.onesms.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.github.ajalt.reprint.core.Reprint
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.view_header.view.*
import kotlinx.android.synthetic.main.view_infomation.view.*
import kotlinx.android.synthetic.main.view_switch.view.*
import root.onesms.R
import root.onesms.Util.BaseActivity

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var contentArray = arrayListOf("설정", "서비스 설정", "정보", "문자 키워드", "해제 암호", "연락 번호", "추가 정보")
        Reprint.initialize(this)
        Log.d("xxx", "" + Reprint.isHardwarePresent())
        if(Reprint.isHardwarePresent()){
            contentArray.add(2, "지문 잠금 활성화")
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingAdapter(contentArray)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menu_preview -> {
                Log.d("hello world", "nice")
                true
            }
            R.id.menu_guide -> {
                Log.d("hello world", "nice")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class SettingAdapter(contentArray : ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        var contentArray : ArrayList<String>? = null

        init {
            this.contentArray = contentArray
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder?{
            val title = contentArray!![viewType]
            var viewNum : Int

            val view = LayoutInflater.from(parent.context).inflate(when{
                title.length == 2 -> {
                    viewNum = 1
                    R.layout.view_header
                }
                title.equals("서비스 설정") || title.equals("지문 잠금 활성화") -> {
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
            val title = contentArray?.get(position)
            title.let {
                when{
                    title?.length == 2 -> {
                        val holder = hl as HeaderViewHolder
                        holder.bindView(title)
                    }
                    title.equals("서비스 설정") || title.equals("지문 잠금 활성화") -> {
                        val holder = hl as SwitchViewHolder
                        holder.bindView(title!!)
                    }
                    else -> {
                        val holder = hl as ContentViewHolder
                        holder.bindView(title!!, "hello world")
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return contentArray?.size ?: 0
        }

        inner class ContentViewHolder(view : View) : RecyclerView.ViewHolder(view){

            fun bindView(title: String, content: String) {
                with(itemView){
                    titleText.text = title
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

            fun bindView(title: String){
                with(itemView){
                    infoText.text = title
                }
            }
        }
    }

}
