package root.onesms.Manager

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager

/**
 * Created by root1 on 2017. 10. 26..
 */
class LockScreenManager(context: Context) {
    var windowManager: WindowManager? = null
    var inflator: LayoutInflater? = null

    init {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE)!! as WindowManager
        inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)!! as LayoutInflater


    }
}