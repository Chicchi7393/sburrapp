package me.chicchi7393.sburrapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun BottomBar(selectedItem: MutableState<Int>) =
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem.value == 0,
            onClick = { selectedItem.value = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Amici") },
            label = { Text("Amici") },
            selected = selectedItem.value == 1,
            onClick = { selectedItem.value = 1 }
        )
    }