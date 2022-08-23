package com.swastik.musicplayer


import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {


    var position: Int = 0

    private var isplaying = true

    companion object {

        private lateinit var musiclist: ArrayList<music>

        private var mediaPlayer: MediaPlayer? = null
        private var Class: String? = String()
    }
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)


        position = intent.getIntExtra("index", 0)
        Class = intent.getStringExtra("class")

        initializelayout()

        next10.setOnClickListener {
            if (mediaPlayer!!.isPlaying){
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition+10000)
            }
        }
        back10.setOnClickListener {
            if (mediaPlayer!!.isPlaying){
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition-10000)
            }
        }


        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromuser: Boolean) {
                if (fromuser) {
                    mediaPlayer!!.seekTo(progress)
                    setdata()
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(p0: SeekBar?) = Unit

        })
        playing_player.setOnClickListener {
            if (isplaying) {

                pausemusic()

            } else {

                playmusic()
            }
        }

        mediaPlayer!!.setOnCompletionListener {
            next_player.performClick()
        }

        previous_player.setOnClickListener {
            setsong(check = false)
        }

        next_player.setOnClickListener {

            setsong(check = true)
        }


    }

    private fun setsong(check: Boolean) {
        if (check) {

            setexact(true)
            setdata()
            setmusic()

        } else {

            setexact(false)
            setdata()
            setmusic()
        }
    }

    private fun setexact(check: Boolean) {
        if (check) {
            if (musiclist.size - 1 == position)
                position = 0
            else ++position

        } else {

            if (0 == position)
                position = musiclist.size - 1
            else --position


        }
    }


    private fun initializelayout() {

        when (intent.getStringExtra("class")) {

            "MainAdapter" -> {

                musiclist = ArrayList()
                musiclist.addAll(MainActivity.musicdata)
                setdata()
                setmusic()
                setupseekbar()

            }
        }

    }

    private fun setmusic() {


        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer()
        }
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(musiclist[position].path)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        isplaying = true
        playing_player.setImageResource(R.drawable.pause)

        seekbarstart.text = setduration(mediaPlayer!!.currentPosition.toLong())
        seekbarend.text = setduration(mediaPlayer!!.duration.toLong())
        seekbar.progress = 0
        seekbar.max = mediaPlayer!!.duration

    }

    private fun setupseekbar(){
        runnable = Runnable {
            seekbarstart.text = setduration(mediaPlayer!!.currentPosition.toLong())
            seekbar.progress = mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable,200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable,0)
    }

    fun setdata() {

        Glide.with(this).load(musiclist[position].musicimage)
            .apply(RequestOptions().placeholder(R.drawable.defaulyphoto).centerCrop())
            .into(player_image)

        musicname_player.text = musiclist[position].musicname


    }


    fun playmusic() {

        playing_player.setImageResource(R.drawable.pause)
        isplaying = true
        mediaPlayer!!.start()

    }

    fun pausemusic() {

        playing_player.setImageResource(R.drawable.playing)
        isplaying = false
        mediaPlayer!!.pause()

    }
}