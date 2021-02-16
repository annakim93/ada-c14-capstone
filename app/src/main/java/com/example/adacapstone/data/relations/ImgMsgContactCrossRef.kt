package com.example.adacapstone.data.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage

@Entity(
    tableName = "imc_relations",
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
    indices = [
        Index("contactId")
    ]
)
data class ImgMsgContactCrossRef(
    val imgMsgId: Int,
    val contactId: Int
)