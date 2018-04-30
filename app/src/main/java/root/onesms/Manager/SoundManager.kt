package root.onesms.Manager

import android.content.*
import android.media.*



/**
 * Created by root1 on 2017. 10. 26..
 */
class SoundManager(val context: Context) {

    lateinit var ringtone: Ringtone

    init {
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE).let {
            RingtoneManager.getRingtone(context, it)
        }.apply { audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(); play() }
    }

    public fun stopSound(){
        ringtone.stop()
    }

}