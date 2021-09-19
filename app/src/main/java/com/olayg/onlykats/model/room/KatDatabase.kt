package com.olayg.onlykats.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olayg.onlykats.repo.local.KatDataDao

@Database(entities = [KatData::class], version = 1, exportSchema = false)
abstract class KatDatabase : RoomDatabase() {

    abstract fun katDao(): KatDataDao

    companion object {
        @Volatile
        private var INSTANCE: KatDatabase? = null

        fun getInstance(context: Context): KatDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KatDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}