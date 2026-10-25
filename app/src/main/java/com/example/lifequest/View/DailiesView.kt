package com.example.lifequest.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lifequest.Components.AddDailyButton
import com.example.lifequest.Components.AddTaskButton
import com.example.lifequest.Components.CardCategoryHome
import com.example.lifequest.Components.CardViewHome
import com.example.lifequest.Components.CartButton
import com.example.lifequest.Components.DailyCard
import com.example.lifequest.Components.MainScaffold
import com.example.lifequest.Components.ProductCardHome
import com.example.lifequest.Components.TaskCard
import com.example.lifequest.Components.Title
import com.example.lifequest.Components.spaceH
import com.example.lifequest.Components.spaceV
import com.example.lifequest.Data.database.AppDatabase
import com.example.lifequest.Data.repository.CartRepository
import com.example.lifequest.Data.repository.CategoryRepository
import com.example.lifequest.Data.repository.DailyRepository
import com.example.lifequest.Data.repository.ProductRepository
import com.example.lifequest.Data.repository.TaskRepository
import com.example.lifequest.R
import com.example.lifequest.ViewModel.CartViewModel
import com.example.lifequest.ViewModel.CartViewModelFactory
import com.example.lifequest.ViewModel.DailyViewModel
import com.example.lifequest.ViewModel.DailyViewModelFactory
import com.example.lifequest.ViewModel.HomeViewModel
import com.example.lifequest.ViewModel.HomeViewModelFactory
import com.example.lifequest.ViewModel.ProductViewModel
import com.example.lifequest.ViewModel.ProductViewModelFactory
import com.example.lifequest.ViewModel.TaskViewModel
import com.example.lifequest.ViewModel.TaskViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailiesView(onItemSelected: (Int) -> Unit, onAddDailyClick: () -> Unit) {
    MainScaffold(
        selectedItem = 1,
        onItemSelected = onItemSelected,
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Title(text = "Diarias")
                    }
                    //colors = TopAppBarDefaults.topAppBarColors(
                    //containerColor = MaterialTheme.colorScheme.Red
                )
                //SearchBarComponent()
            }
        },
        floatingActionButton = {
            AddDailyButton(onClick = onAddDailyClick)
        }
    ) { padding ->
        ContentDailiesView(
            Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@Composable
fun ContentDailiesView(modifier: Modifier = Modifier){
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    // Repositories
    val dailyRepository = DailyRepository(database.dailyDao())
    val dailyViewModel: DailyViewModel = viewModel(factory = DailyViewModelFactory(dailyRepository))

    val dailies by dailyViewModel.products.collectAsState()

    if (dailies.isEmpty()){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No has añadido diarias.", fontSize = 20.sp)
        }
        return
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(dailies){daily ->
            DailyCard(
                daily = daily,
                onComplete = {
                    dailyViewModel.completeDaily(daily.dailyId)
                },
                onDelete = {
                    dailyViewModel.removeDaily(daily.dailyId)
                }
            )
        }
    }
}

@Preview
@Composable
fun DailiesViewPreview(){
    DailiesView(
        onItemSelected = {},
        onAddDailyClick = {}
    )
}