package com.example.adacapstone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.adacapstone.data.relations.ImgMsgWithContacts

@Dao
interface ImgMsgWithContactsDao {

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addImgMsgContactPair(join: ImgMsgWithContacts)

    @Transaction
    @Query("SELECT * FROM img_msg_table WHERE imgMsgId = :imgMsgId ORDER BY imgMsgId")
    suspend fun getContactsOfImgMsg(imgMsgId: Int): LiveData<List<ImgMsgWithContacts>>

}