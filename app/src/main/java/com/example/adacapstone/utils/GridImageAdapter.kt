package com.example.adacapstone.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.databinding.SquareImageViewBinding

class GridImageAdapter(val clickListener: ImgMsgListener) :
    ListAdapter<ImageMessage, GridImageAdapter.ViewHolder>(ImgMsgDiffCallback()) {

    private var imgMsgList = emptyList<ImageMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<SquareImageView>(R.id.squareImage).load(imgMsgList[position].image)
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: SquareImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageMessage, clickListener: ImgMsgListener) {
            binding.imgMsg = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SquareImageViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun setData(imgMsgs: List<ImageMessage>) {
        this.imgMsgList = imgMsgs
        notifyDataSetChanged()
    }
}

class ImgMsgDiffCallback : DiffUtil.ItemCallback<ImageMessage>() {
    override fun areItemsTheSame(oldItem: ImageMessage, newItem: ImageMessage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageMessage, newItem: ImageMessage): Boolean {
        return oldItem == newItem
    }
}

class ImgMsgListener(val clickListener: (imgMsgId: Int) -> Unit) {
    fun onClick(imgMsg: ImageMessage) = clickListener(imgMsg.id)
}