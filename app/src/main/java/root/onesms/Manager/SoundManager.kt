package root.onesms.Manager

import android.content.Context
import android.media.MediaPlayer
import root.onesms.R

/**
 * Created by root1 on 2017. 10. 26..
 */
class SoundManager(context: Context) {
    lateinit var mediaPlayer : MediaPlayer;

    init {
        mediaPlayer = MediaPlayer.create(context, R.raw.siren)
        mediaPlayer.start()
    }

    public fun stopSound(){
        mediaPlayer.stop()
    }

}