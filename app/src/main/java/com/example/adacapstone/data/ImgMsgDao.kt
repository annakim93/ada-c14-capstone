package com.example.adacapstone.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.adacapstone.data.model.ImageMessage

@Dao
interface ImgMsgDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImgMsg(im: ImageMessage)

    @Query("SELECT * FROM img_msg_table ORDER BY id")
    fun readAllData(): LiveData<List<ImageMessage>>

    @Query("SELECT * FROM img_msg_table WHERE id = :key")
    fun getImgMsgWithId(key: Long): LiveData<ImageMessage>

}