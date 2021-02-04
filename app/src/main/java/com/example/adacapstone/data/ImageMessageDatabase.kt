package com.example.adacapstone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageMessage::class], version = 1, exportSchema = false)
abstract class ImageMessageDatabase : RoomDatabase() {

    abstract fun imgMsgDao(): ImageMessageDao

    companion object {
        @Volatile // writes are immediately made visible to other threads
        private var INSTANCE: ImageMessageDatabase? = null

        fun getDatabase(context: Context): ImageMessageDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageMessageDatabase::class.java,
                    "img_msg_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}