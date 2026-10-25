@file:Suppress("PackageName", "unused")

package com.example.lifequest.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifequest.Data.model.Product
import com.example.lifequest.Data.repository.ProductRepository
import com.example.lifequest.R
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    val products: StateFlow<List<Product>> = repository.allProducts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        checkAndInsertInitialData()
    }

    private fun checkAndInsertInitialData() {
        viewModelScope.launch {
            if (repository.getProductCount() == 0) {
                val initialProducts = listOf(
                    Product(name = "Pechuga de Pollo", image = R.drawable.outline_checklist_24, price = 85.0f, measure = "kg", isOffer = true),
                    Product(name = "Leche Entera", image = R.drawable.outline_checklist_24, price = 25.0f, measure = "und", isOffer = false),
                    Product(name = "Aguacate Hass", image = R.drawable.outline_checklist_24, price = 60.0f, measure = "kg", isOffer = true),
                    Product(name = "Galletas Marías", image = R.drawable.outline_checklist_24, price = 15.0f, measure = "pza", isOffer = false),
                )
                initialProducts.forEach { repository.insertProduct(it) }
            }
        }
    }
}
