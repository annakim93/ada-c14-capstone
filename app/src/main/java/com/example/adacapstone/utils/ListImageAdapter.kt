package com.example.adacapstone.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.databinding.SquareImageViewBinding
import com.example.adacapstone.fragments.ManageGridDirections

class ListImageAdapter() : RecyclerView.Adapter<ListImageAdapter.MyViewHolder>() {

    private var imgMsgList = emptyList<ImageMessage>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.square_image_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = imgMsgList[position]
        holder.itemView.findViewById<SquareImageView>(R.id.squareImage).load(imgMsgList[position].image)

//        if (holder.itemView.findViewById<TextView>(R.id.update_fragment_header) != null) {
//            holder.itemView.findViewById<RelativeLayout>(R.id.squareLayout).setOnClickListener {
//                Toast.makeText(holder.itemView.context, "$currentItem", Toast.LENGTH_LONG).show()
////                val action = ManageGridDirections.actionManageGridToUpdateFragment(currentItem)
////                val navController = Navigation.findNavController(holder.itemView)
////                navController.navigate(action)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return imgMsgList.size
    }

    fun setData(imgMsgs: List<ImageMessage>) {
        this.imgMsgList = imgMsgs
        notifyDataSetChanged()
    }

}
