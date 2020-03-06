package com.task1.shoppinglistkotlin.Data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.task1.shoppinglistkotlin.Model.Product

/**
 *  Data Access Object for product
 */
@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Product>

    @Insert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts() }