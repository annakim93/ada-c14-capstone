package com.example.adacapstone.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "img_msg_table")
data class ImageMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val msg: String,
    val imageFilePath: String
) : Parcelable