package com.example.adacapstone.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.adacapstone.R

class GridImageAdapter(context: Context, layoutResource: Int, imgURLs: ArrayList<String>) :
        ArrayAdapter<String?>(context, layoutResource, imgURLs as List<String?>) {

    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val layoutResource = layoutResource

    private class ViewHolder {
        lateinit var image: SquareImageView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Similar to RecyclerView but only loads a few imgs at a time (faster)
        var holder: ViewHolder
        var convertView = convertView

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false)
            holder = ViewHolder()
            holder.image = convertView!!.findViewById(R.id.squareImage) as SquareImageView
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val imgURL = getItem(position)

        return convertView
    }
}
