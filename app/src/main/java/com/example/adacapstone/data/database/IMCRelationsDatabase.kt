package com.example.adacapstone.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adacapstone.data.dao.IMCRelationsDao
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef

@Database(entities = [ImgMsgContactCrossRef::class], version = 1, exportSchema = false)
abstract class IMCRelationsDatabase : RoomDatabase() {

    abstract fun imcRelationsDao(): IMCRelationsDao

    companion object {
        @Volatile
        private var INSTANCE: IMCRelationsDatabase? = null

        fun getDatabase(context: Context): IMCRelationsDatabase {
            val tempInstance = IMCRelationsDatabase.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        IMCRelationsDatabase::class.java,
                        "imc_relations_database"
                ).build()
                IMCRelationsDatabase.INSTANCE = instance
                return instance
            }
        }
    }

}