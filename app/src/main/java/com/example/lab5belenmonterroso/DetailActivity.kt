package com.example.lab5belenmonterroso

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab5belenmonterroso.ui.theme.Lab5BelenMonterrosoTheme
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import android.content.Context
import android.content.SharedPreferences

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val concertId = intent.getIntExtra("concertId", -1)


        val concerts = listOf(
            Concert(1, "https://imagenes.extra.ec/files/image_full/uploads/2023/09/19/650a6b212c7ab.jpeg", "Concert Wos", "Majadas"),
            Concert(2, "https://www.guatemala.com/fotos/2019/10/Concierto-de-Los-Tigres-del-Norte-en-Xela-885x500.jpg", "Concert Los Tigres del Norte", "Guadalajara"),
            Concert(3, "https://www.rockaxis.com/img/newsList/9435451.jpg", "Concert Arctic Monkeys","Inglaterra"),
            Concert(4, "https://laagendacr.com/wp-content/uploads/2022/01/Captura-de-pantalla-2014-08-27-a-las-02.49.08.png", "Concert Los Ángeles Azules","uerto Rico"),
            Concert(5, "https://i.ytimg.com/vi/RSvj0-kNZ2g/maxresdefault.jpg", "Concert 21 Pilots","Cardales de Cayalá"),
            Concert(6, "https://www.guatemala.com/fotos/2023/12/Tributo-a-Adele-en-la-Ciudad-de-Guatemala-Enero-2024-885x500.jpg", "Concert Adele","Canada"),
            Concert(7, "https://monterreyrock.com/wp-content/uploads/2019/08/noah-pino-palo-2.jpg", "Concert Noah Pino Palo","México DF"),
            Concert(8, "https://www.argentina.gob.ar/sites/default/files/trueno-show.jpeg", "Concert Trueno","Argentina")
        )

        val selectedConcert = concerts.find { it.id == concertId }

        setContent {
            Lab5BelenMonterrosoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    selectedConcert?.let {
                        ConcertDetailScreen(concert = it, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertDetailScreen(concert: Concert, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    val isFavorite = sharedPreferences.getBoolean(concert.id.toString(), false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text(concert.title, color = Color.White) },
            navigationIcon = {
                IconButton(onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2196F3))
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF246497)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(concert.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .background(Color.White)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = concert.supportingText,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fecha", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp))
            }
            Text("Hora", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp))
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text("About", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "El rapero argentino WOS ofrecerá un concierto el sábado 16 de septiembre en Fórum Majadas, zona 11 de la ciudad de Guatemala, a las 18 horas.",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // buttoms de acción
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val editor = sharedPreferences.edit()
                    if (isFavorite) {

                        editor.remove(concert.id.toString())
                    } else {

                        editor.putBoolean(concert.id.toString(), true)
                    }
                    editor.apply() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5))
            ) {
                Text("Favorite")
            }
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5))
            ) {
                Text("Buy")
            }
        }
    }
}


