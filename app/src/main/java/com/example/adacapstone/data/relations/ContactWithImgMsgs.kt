package com.example.adacapstone.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage

data class ContactWithImgMsgs(
    @Embedded val contact: Contact,
    @Relation(
        parentColumn = "contactId",
        entityColumn = "imgMsgId",
        associateBy = Junction(ImgMsgContactCrossRef::class)
    )
    val imgMsgs: List<ImageMessage>
)