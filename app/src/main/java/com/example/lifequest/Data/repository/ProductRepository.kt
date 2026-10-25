package com.example.lifequest.Data.repository

import com.example.lifequest.Data.dao.ProductDao
import com.example.lifequest.Data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product){
        productDao.insertProduct(product)
    }

    suspend fun getProductById(id:Int) = productDao.getProductById(id)

    suspend fun getProductCount()=productDao.getProductCount()
}