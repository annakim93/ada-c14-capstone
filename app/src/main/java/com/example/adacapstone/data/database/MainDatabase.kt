package com.example.adacapstone.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adacapstone.data.dao.ContactDao
import com.example.adacapstone.data.dao.IMCRelationsDao
import com.example.adacapstone.data.dao.ImgMsgDao
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef

@Database(
    entities = [
        ImgMsgContactCrossRef::class,
        Contact::class,
        ImageMessage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun imcRelationsDao(): IMCRelationsDao
    abstract fun contactDao(): ContactDao
    abstract fun imgMsgDao(): ImgMsgDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        "imc_relations_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}