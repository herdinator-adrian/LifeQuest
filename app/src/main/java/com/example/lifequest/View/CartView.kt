package com.example.lifequest.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lifequest.Components.CartProductCard
import com.example.lifequest.Components.MainScaffold
import com.example.lifequest.Components.Title
import com.example.lifequest.Components.spaceV
import com.example.lifequest.Data.database.AppDatabase
import com.example.lifequest.Data.repository.CartRepository
import com.example.lifequest.Data.repository.ProductRepository
import com.example.lifequest.ViewModel.CartViewModel
import com.example.lifequest.ViewModel.CartViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    onItemSelected: (Int) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val cartRepository = CartRepository(database.cartItemDao())
    val productRepository = ProductRepository(database.productDao())
    val cartViewModel: CartViewModel = viewModel(factory = CartViewModelFactory(cartRepository, productRepository))
    val cartProducts by cartViewModel.cartProducts.collectAsState()

    MainScaffold(
        selectedItem = -1,
        onItemSelected = onItemSelected,
        showBottomBar = false,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Title(text = "Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar al home"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (cartProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tu carrito está vacío", fontSize = 18.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartProducts) { cartProduct ->
                        CartProductCard(
                            cartProduct = cartProduct,
                            onIncrease = { cartViewModel.addToCart(cartProduct.productId.id) },
                            onDelete = { cartViewModel.removeFromCart(cartProduct.productId.id) }
                        )
                    }
                }
                spaceV(16)

                val total = cartProducts.sumOf { (it.productId.price * it.quantity).toDouble() }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "$${String.format("%.2f", total)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = cartProducts.isNotEmpty(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Ir a pagar", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
