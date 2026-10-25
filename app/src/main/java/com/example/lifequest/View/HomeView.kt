package com.example.lifequest.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifequest.Components.CardViewHome
import com.example.lifequest.Components.CartButton
import com.example.lifequest.Components.MainScaffold
import com.example.lifequest.Components.Title
import com.example.lifequest.Components.spaceV
import com.example.lifequest.Data.database.AppDatabase
import com.example.lifequest.Data.repository.CategoryRepository
import com.example.lifequest.R
import com.example.lifequest.ViewModel.HomeViewModel
import com.example.lifequest.ViewModel.HomeViewModelFactory
import com.example.lifequest.ui.theme.AplicacionTallerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp
import com.example.lifequest.Components.DailyCard
import com.example.lifequest.Components.TaskCard
import com.example.lifequest.Data.repository.DailyRepository
import com.example.lifequest.Data.repository.TaskRepository
import com.example.lifequest.ViewModel.DailyViewModel
import com.example.lifequest.ViewModel.DailyViewModelFactory
import com.example.lifequest.ViewModel.TaskViewModel
import com.example.lifequest.ViewModel.TaskViewModelFactory
import com.example.lifequest.ViewModel.ProductViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onItemSelected: (Int) -> Unit,
    onCartClick: () -> Unit
){
    MainScaffold(
        selectedItem = 0,
        onItemSelected = onItemSelected,
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Title(text = "Todos")
                    }
                    //colors = TopAppBarDefaults.topAppBarColors(
                    //containerColor = MaterialTheme.colorScheme.Red
                )
                //SearchBarComponent()
            }
        },
        floatingActionButton = {
        }
    ) {padding ->
        ContentHomeView(padding, Modifier.padding(padding))
    }
}

@Composable
fun ContentHomeView(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
){
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    // Repositories
    val dailyRepository = DailyRepository(database.dailyDao())
    val dailyViewModel: DailyViewModel = viewModel(factory = DailyViewModelFactory(dailyRepository))
    val taskRepository = TaskRepository(database.taskDao())
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(taskRepository))

    val dailies by dailyViewModel.products.collectAsState()
    val tasks by taskViewModel.products.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            SectionHeader("Diarias")
        }
        if (dailies.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No has añadido diarias.", fontSize = 18.sp)
                }
            }
        } else {
            items(dailies) { daily ->
                DailyCard(
                    daily = daily,
                    onComplete = { dailyViewModel.completeDaily(daily.dailyId) },
                    onDelete = { dailyViewModel.removeDaily(daily.dailyId) }
                )
            }
        }

        item {
            SectionHeader("Tareas")
        }
        if (tasks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No has añadido tareas.", fontSize = 18.sp)
                }
            }
        } else {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    onComplete = { taskViewModel.removeTask(task.taskId) }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Ver Todas",
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview(){
    AplicacionTallerTheme{
        HomeView(
            modifier = Modifier,
            onItemSelected = {},
            onCartClick = {}
        )
    }
}