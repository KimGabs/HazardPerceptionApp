package com.example.hazardperception

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

import com.example.hazardperception.ui.theme.HazardPerceptionAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HazardPerceptionAppTheme {
                MainMenuScreen()
            }
        }
    }
}

@Composable
fun MainMenuScreen() {
    // Track the selected bottom navigation item
    var selectedScreen by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onSelect = { selectedScreen = it }
            )
        }
    ) { innerPadding ->
        // Main content
        when (selectedScreen) {
            "Home" -> MainMenuContent(modifier = Modifier.padding(innerPadding))
            "Account" -> Text(
                text = "Account Screen",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
            "Settings" -> Text(
                text = "Settings Screen",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun MainMenuContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Main Menu",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card-like buttons
        MenuCardButton(
            text = "Hazard Perception Test",
            iconRes = R.drawable.ic_hazard, // Replace with your hazard icon resource
            onClick = { /* Navigate to Hazard Perception */ }
        )
        MenuCardButton(
            text = "LTO Exam and Reviewer",
            iconRes = R.drawable.ic_exam, // Replace with your practice mode icon resource
            onClick = { /* Navigate to Practice Mode */ }
        )
        MenuCardButton(
            text = "Road Sign Comprehension Test",
            iconRes = R.drawable.ic_road_sign, // Replace with your reports icon resource
            onClick = { /* Navigate to Reports */ }
        )
        MenuCardButton(
            text = "About",
            iconRes = R.drawable.ic_about, // Replace with your about icon resource
            onClick = { /* Navigate to About */ }
        )
    }
}

@Composable
fun MenuCardButton(text: String, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail or Icon
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp) // Adjust size for the thumbnail
                    .padding(end = 16.dp) // Space between image and text
            )
            // Text
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedScreen: String,
    onSelect: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedScreen == "Home",
            onClick = { onSelect("Home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = selectedScreen == "Account",
            onClick = { onSelect("Account") },
            label = { Text("Account") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") }
        )
        NavigationBarItem(
            selected = selectedScreen == "Settings",
            onClick = { onSelect("Settings") },
            label = { Text("Settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
    }
}