package com.example.adacapstone.adapters

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.drawable.AnimatedVectorDrawable
import android.telephony.SmsManager
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import coil.load
import com.example.adacapstone.R
import com.example.adacapstone.activities.MainActivity
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.viewmodels.IMCRelationsViewModel
import com.example.adacapstone.interfaces.Location
import com.example.adacapstone.utils.DoubleClickListener
import com.example.adacapstone.utils.Permissions
import com.example.adacapstone.utils.SquareImageView
import com.google.android.gms.location.FusedLocationProviderClient
import java.io.File

class ListImageAdapter(
        private var mIMCRelationsViewModel: IMCRelationsViewModel,
        private var mViewLifecycleOwner: LifecycleOwner,
        private var mFusedLocationProviderClient: FusedLocationProviderClient,
        private var mActivity: Activity
        )
    : RecyclerView.Adapter<ListImageAdapter.MyViewHolder>() {

    private var imgMsgList = emptyList<ImageMessage>()
    private lateinit var avd: AnimatedVectorDrawableCompat
    private lateinit var avd2: AnimatedVectorDrawable

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
        val squareImg = holder.itemView.findViewById<SquareImageView>(R.id.squareImage)
        squareImg.load(File(currentItem.imageFilePath))
        val likeAnim = holder.itemView.findViewById<ImageView>(R.id.like_animation)
        val drawable = likeAnim.drawable
        val likeCornerIndicator = holder.itemView.findViewById<ImageView>(R.id.like_indicator)

        squareImg.setOnClickListener(object : DoubleClickListener() {
            override fun onSingleClick(v: View?) {}
            override fun onDoubleClick(v: View?) {
                if (v != null) {
                    // Set up animation
                    likeAnim.alpha = 0.80f
                    if (drawable is AnimatedVectorDrawableCompat) {
                        avd = drawable
                        avd.start()
                    } else if (drawable is AnimatedVectorDrawable) {
                        avd2 = drawable
                        avd2.start()
                    }

                    likeCornerIndicator.visibility = View.VISIBLE

                    // Send texts: message and GMaps location link
                    if (Permissions.checkPermissions(Permissions.SMS_PERMISSION, v.context)) {
                        val smsManager = SmsManager.getDefault()

                        if (ActivityCompat.checkSelfPermission(
                                v.context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                v.context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                mActivity,
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ),
                                Permissions.LOCATION_REQUEST_CODE
                            )
                            return
                        }

                        mFusedLocationProviderClient.lastLocation
                            .addOnSuccessListener { location : android.location.Location? ->
                                if (location != null) {
                                    val lat = location.latitude
                                    val long = location.longitude

                                    mIMCRelationsViewModel.setSendList(currentItem.imgMsgId)
                                    mIMCRelationsViewModel.sendList.observe(mViewLifecycleOwner, Observer { it ->
                                        val contactList = it.first().contacts

                                        for (contact in contactList) {
                                            val phoneNum = contact.phoneNumber
                                            smsManager.sendTextMessage(
                                                phoneNum,
                                                null,
                                                currentItem.msg,
                                                null,
                                                null
                                            )
                                            smsManager.sendTextMessage(
                                                phoneNum,
                                                null,
                                                "Here is my location: http://www.google.com/maps/place/$lat,$long",
                                                null,
                                                null
                                            )
                                        }

                                        return@Observer
                                    })


                                } else {
                                    Toast.makeText(v.context, "Failed to retrieve lat-longs", Toast.LENGTH_SHORT).show()
                                }
                            }
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
