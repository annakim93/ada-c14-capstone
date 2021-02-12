package com.example.adacapstone.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef

@Dao
interface IMCCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIMCCrossRef(crossRef: ImgMsgContactCrossRef)

}