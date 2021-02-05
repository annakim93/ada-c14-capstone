package com.example.adacapstone.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "img_msg_table")
data class ImageMessage(
    val msg: String,
    val image: Bitmap
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}