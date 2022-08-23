package com.swastik.musicplayer

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {


    companion object {
        lateinit var musicdata : ArrayList<music>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchdata()

        if (musicdata.isEmpty()){
            nosongs.setText("No Songs Available ")
            nosongs.visibility = View.VISIBLE
        }

    }



    @SuppressLint("Range")
    private fun fetchdata(){

        musicdata  = ArrayList()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM_ID,MediaStore.Audio.Media.ALBUM)

        val query = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,MediaStore.Audio.Media.DATE_ADDED + " DESC",null)

        if (query != null){
            if (query.moveToFirst()){
                do {
                    val title = query.getString(query.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val path = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val duration = query.getLong(query.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val image = query.getString(query.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val imageuri = Uri.parse("content://media/external/audio/albumart")
                    val musicimage = Uri.withAppendedPath(imageuri,image).toString()
                    val music = music(
                        musicname = title,
                        path = path,
                        duration = duration,
                        musicimage = musicimage
                    )
                    val file = File(path)
                    if (file.exists()){
                        musicdata.add(music)
                        recycler_view.layoutManager = LinearLayoutManager(this)
                        recycler_view.adapter = MusicAdapter(this,musicdata)

                    }

                }while (query.moveToNext())
                query.close()
            }
        }

    }
}