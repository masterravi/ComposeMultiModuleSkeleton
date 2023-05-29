package com.training.trainingmodule.localization.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Config_files")
class ConfigFileEntity(
    @PrimaryKey
    val fileName: String = "",

    val versionName: String = "",

    val contentJson: String = "",

    val contentMap: Map<String, String> = HashMap()
)
