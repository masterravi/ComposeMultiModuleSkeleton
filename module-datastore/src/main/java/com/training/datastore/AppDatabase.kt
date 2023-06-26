package com.training.datastore

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.training.datastore.dao.LanguageDao
import com.training.datastore.dao.ProductDao
import com.training.datastore.entity.ProductEntity
import com.training.datastore.entity.LanguageEntity

@Database(
    entities = [ProductEntity::class, LanguageEntity::class],
    exportSchema = false,
    version = 2
)

@TypeConverters(
    ConverterList::class
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun LanguageDao(): LanguageDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "trainingApp"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Log.d("UPI_DATABASE", "Database created")
                            super.onCreate(db)
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            Log.d("UPI_DATABASE", "Database opened")
                            super.onOpen(db)
                        }
                    })
                        .enableMultiInstanceInvalidation()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}