package com.swastik.musicplayer

import android.net.Uri
import java.util.concurrent.TimeUnit


data class music(
    val musicimage: String,
    val musicname:String,
    val duration: Long,
    val path: String
)

fun setduration(duration: Long):String{
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes* TimeUnit.SECONDS.convert(1,
        TimeUnit.MINUTES)

    return String.format("%02d: %02d",minutes,seconds)
}
