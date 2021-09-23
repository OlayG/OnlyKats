package com.olayg.onlykats.repo.local

import android.content.Context
import androidx.room.*
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.repo.local.dao.BreedDao
import com.olayg.onlykats.repo.local.dao.KatDao
import com.olayg.onlykats.repo.local.utils.KatConvertors

@Database(entities = [Kat::class, Breed::class], version = 1)
@TypeConverters(KatConvertors::class)
abstract class KatDatabase : RoomDatabase()
{
    abstract fun katDao(): KatDao
    abstract fun BreedDao(): BreedDao
    companion object
    {
        private const val DATABASE_NAME = "kat.db"
        @Volatile private var instance : KatDatabase? = null

        fun getInstance(context: Context): KatDatabase
        {
            return instance ?: synchronized(this)
            {
                instance ?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase(context: Context): KatDatabase
        {
            return Room.databaseBuilder(
                context, KatDatabase::class.java, DATABASE_NAME
            ).addTypeConverter(KatConvertors()).build()
        }
    }
}

