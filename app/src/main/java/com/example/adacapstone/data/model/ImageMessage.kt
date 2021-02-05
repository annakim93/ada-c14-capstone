package com.example.adacapstone.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "img_msg_table")
data class ImageMessage(
    val msg: String,
    val image: Bitmap
    ) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}