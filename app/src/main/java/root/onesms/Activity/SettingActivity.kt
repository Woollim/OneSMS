package root.onesms.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_setting.*
import root.onesms.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_preview -> {
                Log.d("hello world", "nice")
                return true
            }
            R.id.menu_guide -> {
                Log.d("hello world", "nice")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    class SettingAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : RecyclerView.ViewHolder{

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return (4 + 2 + 2)
        }

        class ContentViewHolder(view : View) : RecyclerView.ViewHolder(view){

        }

        class HeaderViewHolder(view : View) : RecyclerView.ViewHolder(view){

        }

        class SwitchViewHolder(view : View) : RecyclerView.ViewHolder(view){

        }
    }

}
