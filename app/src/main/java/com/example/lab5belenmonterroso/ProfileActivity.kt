package com.example.lab5belenmonterroso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.lab5belenmonterroso.ui.theme.Lab5BelenMonterrosoTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext


class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5BelenMonterrosoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Profile", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF2196F3)
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

            val backgroundImageUrl = "https://www.estacionsur.ar/wp-content/uploads/2022/03/wos.jpg"
            Image(
                painter = rememberImagePainter(backgroundImageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircleAvatar(imageUrl = "https://guategram.com/wp-content/uploads/2023/06/wos-concierto-guatemala.jpg")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Valentín Oliva",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileOption("Edit Profile", Icons.Default.Edit, onClick = {})
        ProfileOption("Reset Password", Icons.Default.Lock, onClick = {})
        ProfileOptionWithSwitch("Notifications", Icons.Default.Notifications)
        ProfileOption("Favorites", Icons.Default.Favorite, onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        })
    }
}


@Composable
fun CircleAvatar(imageUrl: String) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    ) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun ProfileOption(option: String, icon: ImageVector, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(option) },
        leadingContent = { Icon(imageVector = icon, contentDescription = option) },
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
fun ProfileOptionWithSwitch(option: String, icon: ImageVector) {
    val isPushEnable = remember { mutableStateOf(false) }
    ListItem(
        headlineContent = { Text(option) },
        leadingContent = { Icon(imageVector = icon, contentDescription = option) },
        trailingContent = {
            Switch(
                checked = isPushEnable.value,
                onCheckedChange = { isPushEnable.value = it }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Lab5BelenMonterrosoTheme {
        ProfileScreen()
    }
}

