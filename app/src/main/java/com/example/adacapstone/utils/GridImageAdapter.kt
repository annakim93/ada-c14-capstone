package com.example.adacapstone.utils

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.ImgMsgBinding

class GridImageAdapter(val clickListener: ImgMsgListener) :
    ListAdapter<ImageMessage, GridImageAdapter.ViewHolder>(ImgMsgDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(private val binding: ScriptGroup.Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
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