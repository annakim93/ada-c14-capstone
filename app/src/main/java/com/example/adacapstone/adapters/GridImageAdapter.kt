package com.example.adacapstone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.databinding.SquareImageViewBinding
import com.example.adacapstone.fragments.ManageGridFragment
import com.example.adacapstone.fragments.ManageGridFragmentDirections
import com.example.adacapstone.utils.SquareImageView
import java.io.File

class GridImageAdapter(val clickListener: ImgMsgListener, val fragment: ManageGridFragment) :
    ListAdapter<ImageMessage, GridImageAdapter.ViewHolder>(ImgMsgDiffCallback()) {

    // List to hold data
    var imgMsgList = emptyList<ImageMessage>()

    // Vars for multiple selection
    var multiSelect = false
    val selectedItems = arrayListOf<ImageMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = imgMsgList[position]
        val squareImageView = holder.itemView.findViewById<SquareImageView>(R.id.squareImage)

        squareImageView.load(File(currentItem.imageFilePath))
        holder.bind(getItem(position)!!, clickListener)

        // Selected items have different opacity
        if (selectedItems.contains(currentItem)) {
            squareImageView.alpha = 0.3f
        } else {
            squareImageView.alpha = 1.0f
        }

        // Long-click handler - selection
        squareImageView.setOnLongClickListener {
            if (!multiSelect) {
                multiSelect = true
                selectItem(holder, currentItem)
                fragment.startSelection(currentItem)
            }
            true
        }

        // Normal click handler
        squareImageView.setOnClickListener {
            if (multiSelect) {
                selectItem(holder, currentItem)
                fragment.manageSelection(currentItem)
                if (selectedItems.size == 0) {
                    multiSelect = false
                }
            } else {
                val action = ManageGridFragmentDirections.actionManageGridToUpdateFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
        }
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
        submitList(imgMsgs)
    }

    private fun selectItem(holder: ViewHolder, imgMsg: ImageMessage) {
        if (selectedItems.contains(imgMsg)) {
            selectedItems.remove(imgMsg)
            holder.itemView.findViewById<SquareImageView>(R.id.squareImage).alpha = 1.0f
        } else {
            selectedItems.add(imgMsg)
            holder.itemView.findViewById<SquareImageView>(R.id.squareImage).alpha = 0.3f
        }
    }
}

class ImgMsgDiffCallback : DiffUtil.ItemCallback<ImageMessage>() {
    override fun areItemsTheSame(oldItem: ImageMessage, newItem: ImageMessage): Boolean {
        return oldItem.imgMsgId == newItem.imgMsgId
    }

    override fun areContentsTheSame(oldItem: ImageMessage, newItem: ImageMessage): Boolean {
        return oldItem == newItem
    }
}

class ImgMsgListener(val clickListener: (imgMsg: ImageMessage) -> Unit) {
    fun onClick(imgMsg: ImageMessage) = clickListener(imgMsg)
}