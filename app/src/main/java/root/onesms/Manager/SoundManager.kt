package root.onesms.Manager

import android.content.*
import android.media.*
import root.onesms.*
import android.media.RingtoneManager



/**
 * Created by root1 on 2017. 10. 26..
 */
class SoundManager(val context: Context) {

    lateinit var ringtone: Ringtone

    init {
        val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        val uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(context, uri)
        ringtone.audioAttributes = audioAttributes
        ringtone.play()
    }

    public fun stopSound(){
        ringtone.stop()
    }

}