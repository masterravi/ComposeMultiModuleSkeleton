package com.training.localization.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.training.trainingmodule.localization.data.room.entity.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(languageEntity: LanguageEntity)

    @Query("Select * from  language_entity where languageName =:language limit 1")
    fun getLanguageData(language: String): List<LanguageEntity>

    @Query("Select * from  language_entity where languageName =:language limit 1")
    fun getLanguageDataFlow(language: String): Flow<List<LanguageEntity>>
}
