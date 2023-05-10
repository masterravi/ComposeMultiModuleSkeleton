package com.training.datastore.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_entity")
data class ProductEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "title")
    val title: String
)