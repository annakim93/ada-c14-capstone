package com.example.adacapstone.data.relations

import androidx.room.Entity

@Entity(primaryKeys = ["imgMsgId", "contactId"])
data class ImgMsgContactCrossRef(
    val imgMsgId: Int,
    val contactId: Int
)