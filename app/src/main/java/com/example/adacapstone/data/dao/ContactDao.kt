package com.example.adacapstone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY contactId")
    fun readAllData(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE contactId = :contactId")
    fun getContactWithId(contactId: Int): LiveData<Contact>

}