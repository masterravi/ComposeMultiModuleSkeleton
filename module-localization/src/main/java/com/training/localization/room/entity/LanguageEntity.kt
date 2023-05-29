package com.training.trainingmodule.localization.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_entity")
data class LanguageEntity(
    @PrimaryKey
    val languageName: String,

    val languageJson: String,

    val languageKeySet: Map<String, Any>,

    val versionName: String
)
