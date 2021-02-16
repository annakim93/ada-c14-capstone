package com.example.adacapstone.adapters

import android.telephony.SmsManager
import android.view.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodels.IMCRelationsViewModel
import com.example.adacapstone.utils.DoubleClickListener
import com.example.adacapstone.utils.Permissions
import com.example.adacapstone.utils.SquareImageView
import java.io.File

class ListImageAdapter(
        private var mIMCRelationsViewModel: IMCRelationsViewModel,
        private var mViewLifecycleOwner: LifecycleOwner
        )
    : RecyclerView.Adapter<ListImageAdapter.MyViewHolder>() {

    private var imgMsgList = emptyList<ImageMessage>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.square_image_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = imgMsgList[position]
        val imageView = holder.itemView.findViewById<SquareImageView>(R.id.squareImage)
        imageView.load(File(currentItem.imageFilePath))

        imageView.setOnClickListener(object : DoubleClickListener() {
            override fun onSingleClick(v: View?) {}
            override fun onDoubleClick(v: View?) {
                if (v != null) {
                    if (Permissions.checkPermissions(Permissions.SMS_PERMISSION, v.context)) {
                        val smsManager = SmsManager.getDefault()
                        mIMCRelationsViewModel.setSendList(currentItem.imgMsgId)
                        mIMCRelationsViewModel.sendList.observe(mViewLifecycleOwner, Observer { it ->
                            val contactList = it.first().contacts

                            for (contact in contactList) {
                                val phoneNum = contact.phoneNumber
                                smsManager.sendTextMessage(phoneNum, null, currentItem.msg, null, null)
                            }

                            return@Observer
                        })
                    }
                }
            }
        })

    }

    override fun getItemCount(): Int {
        return imgMsgList.size
    }

    fun setData(imgMsgs: List<ImageMessage>) {
        this.imgMsgList = imgMsgs
        notifyDataSetChanged()
    }

}
