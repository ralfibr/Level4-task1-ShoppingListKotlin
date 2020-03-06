package com.task1.shoppinglistkotlin.Data

import android.content.Context
import com.task1.shoppinglistkotlin.Model.Product


class ProductRepository(context: Context) {

    private val productDao: ProductDao

    init {
        val database = ShoppingListRoomDatabase.getDatabase(context)
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Product> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    suspend fun deleteAllProducts() = productDao.deleteAllProducts()

}
