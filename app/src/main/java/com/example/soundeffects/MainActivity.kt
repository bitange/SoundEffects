package com.example.soundeffects

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.xml.sax.ext.DeclHandler

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    //defined the mediaplayer instance/object of a class
    private var mediaPlayer: MediaPlayer? =null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar=findViewById(R.id.sbThunder)
        handler= Handler(Looper.getMainLooper())



        val playButton=findViewById<FloatingActionButton>(R.id.fapPlay)
        playButton.setOnClickListener {

            if (mediaPlayer==null){
                mediaPlayer=MediaPlayer.create(this,R.raw.thunderumble)
                initializeSeekBar()
            }
            mediaPlayer?.start()

        }
        val pauseButton=findViewById<FloatingActionButton>(R.id.fapPause)
        pauseButton.setOnClickListener {
                mediaPlayer?.pause()
        }
        val stopButton= findViewById<FloatingActionButton>(R.id.fapStop)
        stopButton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer=null
            handler.removeCallbacks(runnable)
            seekBar.progress=0
        }
    }
    private fun initializeSeekBar(){
         seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
             override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                 if (fromUser) mediaPlayer?.seekTo(progress)
             }

             override fun onStartTrackingTouch(seekBar: SeekBar?) {

             }

             override fun onStopTrackingTouch(seekBar: SeekBar?) {

             }

         })
        val tvPlayed= findViewById<TextView>(R.id.tvPlayed)
        val tvDue= findViewById<TextView>(R.id.tvDue)
        seekBar.max=mediaPlayer!!.duration
        runnable=Runnable{
            seekBar.progress=mediaPlayer!!.currentPosition

            val playedTime=mediaPlayer!!.currentPosition/1000
            tvPlayed.text="$playedTime sec"
            val duration=mediaPlayer!!.duration/1000
            val dueTime=duration-playedTime
            tvDue.text="$dueTime sec"

             handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)
    }
}