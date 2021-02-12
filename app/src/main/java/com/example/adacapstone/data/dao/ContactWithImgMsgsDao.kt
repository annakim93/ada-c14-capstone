package com.example.adacapstone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.adacapstone.data.relations.ContactWithImgMsgs

@Dao
interface ContactWithImgMsgsDao {

    @Transaction
    @Query("SELECT * FROM contact_table WHERE contactId = :contactId ORDER BY contactId")
    suspend fun getImgMsgsOfContact(contactId: Int): LiveData<List<ContactWithImgMsgs>>

}