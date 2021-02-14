package com.example.adacapstone.data.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage

@Entity(
    primaryKeys = ["imgMsgId", "contactId"],
    foreignKeys = [
        ForeignKey(
            entity = ImageMessage::class,
            parentColumns = ["imgMsgId"],
            childColumns = ["imgMsgId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["contactId"],
            childColumns = ["contactId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    tableName = "imc_relations"
)
data class ImgMsgContactCrossRef(
    val imgMsgId: Int,
    val contactId: Int
)