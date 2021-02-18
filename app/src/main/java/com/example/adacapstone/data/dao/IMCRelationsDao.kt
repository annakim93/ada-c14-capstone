package com.example.adacapstone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.adacapstone.data.relations.ContactWithImgMsgs
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef
import com.example.adacapstone.data.relations.ImgMsgWithContacts

@Dao
interface IMCRelationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIMCCrossRef(crossRef: ImgMsgContactCrossRef)

    @Delete
    suspend fun deleteIMCCrossRef(crossRef: ImgMsgContactCrossRef)

    @Transaction
    @Query("SELECT * FROM img_msg_table WHERE imgMsgId = :imgMsgId ORDER BY imgMsgId")
    fun getContactsOfImgMsg(imgMsgId: Int): LiveData<List<ImgMsgWithContacts>>

    @Transaction
    @Query("SELECT * FROM contact_table WHERE contactId = :contactId ORDER BY contactId")
    fun getImgMsgsOfContact(contactId: Int): LiveData<List<ContactWithImgMsgs>>

}