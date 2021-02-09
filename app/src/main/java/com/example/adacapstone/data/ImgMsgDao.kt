package com.example.adacapstone.data

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

    @Query("SELECT * FROM img_msg_table ORDER BY id")
    fun readAllData(): LiveData<List<ImageMessage>>

    @Query("SELECT * FROM img_msg_table WHERE id = :imgMsgId")
    fun getImgMsgWithId(imgMsgId: Int): LiveData<ImageMessage>

}