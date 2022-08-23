package com.swastik.musicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.songview.view.*

class MusicAdapter( val context: Context,private val musiclist: ArrayList<music>) :
    RecyclerView.Adapter<MusicAdapter.viewholder>() {

    class viewholder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.image_view
        val musicname = view.musicname_view
        val duration = view.duration_view

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.songview, parent, false)
        return viewholder(itemview)

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        val mposition = musiclist[position]

        holder.duration.text = setduration(mposition.duration)
        holder.musicname.text = mposition.musicname
        Glide.with(holder.itemView.context).load(mposition.musicimage)
            .apply(RequestOptions().placeholder(R.drawable.defaulyphoto).centerCrop())
            .into(holder.image)


        holder.itemView.setOnClickListener {
            val intent = Intent(context,PlayerActivity::class.java)
            intent.putExtra("index",position)
            intent.putExtra("class","MainAdapter")
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {

        return musiclist.size

    }
}