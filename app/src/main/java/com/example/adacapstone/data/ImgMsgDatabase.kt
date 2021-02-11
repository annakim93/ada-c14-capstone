package com.example.adacapstone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.adacapstone.data.dao.ImgMsgDao
import com.example.adacapstone.data.model.ImageMessage

@Database(entities = [ImageMessage::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ImgMsgDatabase : RoomDatabase() {

    abstract fun imgMsgDao(): ImgMsgDao

    companion object {
        @Volatile // writes are immediately made visible to other threads
        private var INSTANCE: ImgMsgDatabase? = null

        fun getDatabase(context: Context): ImgMsgDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImgMsgDatabase::class.java,
                    "img_msg_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}