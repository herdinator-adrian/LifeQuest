package com.example.lifequest.Data.repository

import com.example.lifequest.Data.dao.CartDao
import com.example.lifequest.Data.model.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    val cartItems: Flow<List<CartItem>> = cartDao.getCartItems()

    suspend fun AddItem(productId: Int){
        val existingItem = cartDao.getCartItemById(productId)
        if (existingItem != null){
            cartDao.insertOrUpdate(existingItem.copy(quantity = existingItem.quantity))
        } else{
            cartDao.insertOrUpdate(CartItem(productId, 1))
        }
    }

    suspend fun UpdateQuantity(productId: Int, newQuantity: Int){
        if (newQuantity <= 0){
            cartDao.getCartItemById(productId)?.let{
                cartDao.deleteCartItem(it)
            }
        } else{
            cartDao.insertOrUpdate(CartItem(productId, newQuantity))
        }
    }

    suspend fun DeleteItem(productId: Int){
        cartDao.getCartItemById(productId)?.let{
            cartDao.deleteCartItem(it)
        }
    }
}