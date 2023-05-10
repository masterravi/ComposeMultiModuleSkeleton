package com.training.datastore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.training.datastore.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_entity")
    suspend fun getAllAsset(): List<ProductEntity>

    @Query("SELECT * FROM product_entity where title=:assetName")
    fun getAssetById(assetName: String): Flow<ProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ProductEntity)

    @Query("SELECT * FROM product_entity")
    fun getAllAssetFile(): Flow<List<ProductEntity>>
}