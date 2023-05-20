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
    fun getAllProduct(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product_entity where id=:productId")
    fun getProductById(productId: Int): Flow<ProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ProductEntity)
}