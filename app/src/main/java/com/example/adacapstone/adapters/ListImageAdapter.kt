package com.example.adacapstone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.utils.SquareImageView
import java.io.File

class ListImageAdapter() : RecyclerView.Adapter<ListImageAdapter.MyViewHolder>() {

    private var imgMsgList = emptyList<ImageMessage>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.square_image_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = imgMsgList[position]
        val imageView = holder.itemView.findViewById<SquareImageView>(R.id.squareImage)
        imageView.load(File(currentItem.imageFilePath))
    }

    override fun getItemCount(): Int {
        return imgMsgList.size
    }

    fun setData(imgMsgs: List<ImageMessage>) {
        this.imgMsgList = imgMsgs
        notifyDataSetChanged()
    }

}
