package com.example.adacapstone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.adacapstone.data.model.ImageMessage

@Dao
interface ImgMsgDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImgMsg(im: ImageMessage)

    @Update
    suspend fun updateImgMsg(im: ImageMessage)

    @Delete
    suspend fun deleteImgMsg(im: ImageMessage)

    @Query("SELECT * FROM img_msg_table ORDER BY imgMsgId")
    fun readAllData(): LiveData<List<ImageMessage>>

    @Query("SELECT * FROM img_msg_table WHERE imgMsgId = :imgMsgId")
    fun getImgMsgWithId(imgMsgId: Int): LiveData<ImageMessage>

}