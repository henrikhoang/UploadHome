package com.example.uploadhome

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_picture.view.*

class PictureAdapter(private val context : Context?,
                     private val dataSet : ArrayList<Uri>?) : RecyclerView.Adapter<PictureAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_picture, parent, false))

    override fun getItemCount(): Int {
        dataSet ?: return 0
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.iv_house_photo.setImageURI(dataSet?.get(position))
        }
    }
}