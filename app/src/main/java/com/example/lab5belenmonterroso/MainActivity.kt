package com.example.lab5belenmonterroso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.lab5belenmonterroso.ui.theme.Lab5BelenMonterrosoTheme
import androidx.compose.material3.TopAppBarDefaults
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.SharedPreferences


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5BelenMonterrosoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UpcomingConcertsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Concert(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val supportingText: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingConcertsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    val concerts = listOf(
        Concert(
            id = 1,
            imageUrl = "https://imagenes.extra.ec/files/image_full/uploads/2023/09/19/650a6b212c7ab.jpeg",
            title = "Concert Wos",
            supportingText = "Majadas Guatemala"
        ),
        Concert(
            id = 2,
            imageUrl = "https://www.guatemala.com/fotos/2019/10/Concierto-de-Los-Tigres-del-Norte-en-Xela-885x500.jpg",
            title = "Concert Los Tigres del Norte",
            supportingText = "Guadalajara"
        ),
        Concert(
            id = 3,
            imageUrl = "https://www.rockaxis.com/img/newsList/9435451.jpg",
            title = "Concert Arctic Monkeys",
            supportingText = "Inglaterra"
        ),
        Concert(
            id = 4,
            imageUrl = "https://laagendacr.com/wp-content/uploads/2022/01/Captura-de-pantalla-2014-08-27-a-las-02.49.08.png",
            title = "Concert Los Ángeles Azules",
            supportingText = "Puerto Rico"
        ),
        Concert(
            id = 5,
            imageUrl = "https://i.ytimg.com/vi/RSvj0-kNZ2g/maxresdefault.jpg",
            title = "Concert 21 Pilots",
            supportingText = "Cardales de Cayalá"
        ),
        Concert(
            id = 6,
            imageUrl = "https://www.guatemala.com/fotos/2023/12/Tributo-a-Adele-en-la-Ciudad-de-Guatemala-Enero-2024-885x500.jpg",
            title = "Concert Adele",
            supportingText = "Canada"
        ),
        Concert(
            id = 7,
            imageUrl = "https://monterreyrock.com/wp-content/uploads/2019/08/noah-pino-palo-2.jpg",
            title = "Concert Noah Pino Palo",
            supportingText = "México DF"
        ),
        Concert(
            id = 8,
            imageUrl = "https://www.argentina.gob.ar/sites/default/files/trueno-show.jpeg",
            title = "Concert Trueno",
            supportingText = "Argentina"
        )
    )

    // favs
    val favoriteConcerts = concerts.filter {
        sharedPreferences.getBoolean(it.id.toString(), false)
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Todo Eventos", color = Color.White) },
            actions = {
                IconButton(onClick = {  val intent = Intent(context, ProfileActivity::class.java)
                    context.startActivity(intent)}) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Perfil", tint = Color.White)
                }
                IconButton(onClick = { val intent = Intent(context, VenuesActivity::class.java)
                    context.startActivity(intent)}) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF2196F3)
            )
        )


        // favoritos a la lista
        if (favoriteConcerts.isNotEmpty()) {
            Text(
                text = "Your Favorites",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(16.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(favoriteConcerts.size) { index ->
                    ConcertCard(concert = favoriteConcerts[index], onClick = {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("concertId", favoriteConcerts[index].id)
                        context.startActivity(intent)
                    })
                }
            }
        }



        Text(
            text = "All Concerts",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(concerts.size) { index ->
                ConcertCard(concert = concerts[index], onClick = {
                    // pasar a pantalla pasando la ID
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("concertId", concerts[index].id)
                    context.startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun ConcertCard(concert: Concert, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F))
    ) {
        Column {
            Image(
                painter = rememberImagePainter(concert.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = concert.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = concert.supportingText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UpcomingConcertsPreview() {
    Lab5BelenMonterrosoTheme {
        UpcomingConcertsScreen()
    }
}
