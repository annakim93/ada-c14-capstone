package com.example.adacapstone.utils

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.data.ImageMessage

class ListImageAdapter : RecyclerView.Adapter<ListImageAdapter.MyViewHolder>() {

    private var imgMsgList = emptyList<ImageMessage>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.square_image_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = imgMsgList[position]
        holder.itemView.findViewById<SquareImageView>(R.id.squareImage).load(imgMsgList[position].image)
    }

    override fun getItemCount(): Int {
        return imgMsgList.size
    }

    fun setData(imgMsgs: List<ImageMessage>) {
        this.imgMsgList = imgMsgs
        notifyDataSetChanged()
    }

}