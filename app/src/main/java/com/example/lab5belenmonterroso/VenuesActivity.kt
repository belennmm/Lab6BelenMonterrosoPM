package com.example.lab5belenmonterroso

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab5belenmonterroso.ui.theme.Lab5BelenMonterrosoTheme

class VenuesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5BelenMonterrosoTheme {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Venues", color = Color.White) },
                            navigationIcon = {
                                IconButton(onClick = {

                                    val intent = Intent(this@VenuesActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF2196F3)
                            )
                        )
                    },
                    content = { innerPadding ->
                        VenuesScreen(modifier = Modifier.padding(innerPadding).padding(top = 8.dp))
                    }
                )
            }
        }
    }
}

@Composable
fun VenuesScreen(modifier: Modifier = Modifier) {
    // Lista de venues
    VenuesListScreen(
        venues = listOf(
            Venue("Wos", "Fórum Majadas, Guatemala"),
            Venue("Los Tigres del Norte", "Guadalajara, México"),
            Venue("Arctic Monkeys", "Inglaterra"),
            Venue("Los Ángeles Azules", "Puerto Rico"),
            Venue("21 Pilots", "Cardales de Cayalá"),
            Venue("Adele", "Ottawa, Canada"),
            Venue("Noah Pino Palo", "México DF"),
            Venue("Trueno", "Argentina"),
        ),
        modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
}

@Composable
fun VenuesListScreen(venues: List<Venue>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(venues.size) { index ->
            VenueItem(venue = venues[index])

            if (index < venues.size - 1) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun VenueItem(venue: Venue) {
    ListItem(
        headlineContent = { Text(venue.title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = { Text(venue.subtitle, style = MaterialTheme.typography.bodyMedium) },
        leadingContent = {
            Icon(Icons.Default.LocationOn, contentDescription = null)
        },
        trailingContent = {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        },
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

// Datos de prueba para los venues
data class Venue(
    val title: String,
    val subtitle: String
)

@Preview(showBackground = true)
@Composable
fun VenuesListPreview() {
    Lab5BelenMonterrosoTheme {
        VenuesListScreen(
            venues = listOf(
                Venue("Wos", "Fórum Majadas, Guatemala"),
                Venue("Adele", "Ottawa, Canada"),
                Venue("Los Ángeles Azules", "Puerto Rico")
            )
        )
    }
}
