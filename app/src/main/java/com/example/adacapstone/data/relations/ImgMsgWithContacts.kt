package com.example.adacapstone.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage

data class ImgMsgWithContacts(
    @Embedded val imgMsg: ImageMessage,
    @Relation(
        parentColumn = "imgMsgId",
        entityColumn = "contactId",
        associateBy = Junction(ImgMsgContactCrossRef::class)
    )
    val contacts: List<Contact>
)