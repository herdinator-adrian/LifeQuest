package com.example.lifequest.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.lifequest.R

@Composable
fun BottomMenu(
    //Indica elemento seleccionado actualmente
    selectedItem : Int,
    //Recibir una función quye será ejecutada cuando el usuario seleccione una opción
    onItemSelected:(Int) -> Unit
){
    NavigationBar{
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = {
                onItemSelected(0)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio"
                )
            },
            //texto por debajo del ícono
            label = {
                Text("Inicio")
            }
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = {
                onItemSelected(1)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_calendar_month_24),
                    contentDescription = "Diarias"
                )
            },
            label = {
                Text("Diarias")
            }
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = {
                onItemSelected(2)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_checklist_24),
                    contentDescription = "Tareas"
                )
            },
            label = {
                Text("Tareas")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

        )
        //Cuarta opcion: Perfil
        NavigationBarItem(
            selected = selectedItem == 3,
            onClick = {
                onItemSelected(3)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}