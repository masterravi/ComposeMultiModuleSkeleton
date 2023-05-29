package com.training.localization.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.training.localization.room.dao.LanguageDao
import com.training.trainingmodule.localization.data.room.entity.LanguageEntity
import com.training.trainingmodule.localization.utilities.LocalizationLogger



@Database(
    entities = [LanguageEntity::class],
    exportSchema = false,
    version = 43
)
@TypeConverters(
    ConverterList::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun LanguageDao(): LanguageDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "Localization"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            LocalizationLogger.debug("LOCALIZATION_DATABASE", "Database created")

                            super.onCreate(db)
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            LocalizationLogger.debug("LOCALIZATION_DATABASE", "Database opened")
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
