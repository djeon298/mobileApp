package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

data class Content(
    val id: String,
    val title: String,
    val quote: String,
    val image: Int,
    val color: Color,
    val director: String,
    val budget: String,
    val rating: String
)

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "select") {
        composable("select") { SelectScreen(navController) }
        composable("movies") { MovieHome(navController) }
        composable("dramas") { DramaHome(navController) }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val item = (movieList + dramaList).find { it.id == id }
            item?.let { DetailScreen(it, navController) }
        }
    }
}

@Composable
fun SelectScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("콘텐츠 선택", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(40.dp))

        SelectButton("🎬 영화", Color(0xFF4CAF50)) {
            navController.navigate("movies")
        }
        Spacer(Modifier.height(20.dp))
        SelectButton("📺 드라마", Color(0xFF2196F3)) {
            navController.navigate("dramas")
        }
    }
}

@Composable
fun SelectButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .width(220.dp)
            .height(60.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

val movieList = listOf(
    Content(
        "parasite",
        "기생충",
        "너는 다 계획이 있구나",
        R.drawable.parasite,
        Color(0xFF4CAF50),
        "봉준호",
        "150억 원",
        "1000만 명"
    ),
    Content(
        "myeongryang",
        "명량",
        "신에게는 아직 열두 척의 배가 남아있습니다.",
        R.drawable.myeongryang,
        Color(0xFF2196F3),
        "김한민",
        "약 80억 원",
        "1700만 명"
    ),
    Content(
        "crime",
        "범죄도시",
        "진실의 방으로",
        R.drawable.crime_city,
        Color(0xFFD32F2F),
        "강윤성",
        "70억 원",
        "688만 명"
    ),
    Content(
        "tazza",
        "타짜",
        "동작 그만! 지금 밑장빼기여?",
        R.drawable.tazza,
        Color(0xFFFFC107),
        "최동훈",
        "53억 원",
        "684만 명"
    )
)

@Composable
fun MovieHome(navController: NavHostController) {
    ContentList("영화", movieList, navController)
}

val dramaList = listOf(
    Content(
        "sun",
        "태양의 후예",
        "우리는 우리의 삶을 살아간다.",
        R.drawable.sun,
        Color(0xFFFF8A65),
        "이응복",
        "130억 원",
        "최고 38.8%"
    ),
    Content(
        "hotel",
        "호텔 델루나",
        "이 호텔엔 이유가 있어.",
        R.drawable.hotel,
        Color(0xFF9575CD),
        "오충환",
        "약 90억 원",
        "최고 약 12%"
    ),
    Content(
        "our",
        "그 해 우리는",
        "그때 우리는 최선을 다했어.",
        R.drawable.our,
        Color(0xFF4DB6AC),
        "최정효",
        "약 50억 원",
        "최고 약 8%"
    ),
    Content(
        "mr",
        "미스터 션샤인",
        "나는 조선의 노비였습니다.",
        R.drawable.mr,
        Color(0xFF90A4AE),
        "이응복",
        "400억 원",
        "18.1%"
    )
)

@Composable
fun DramaHome(navController: NavHostController) {
    ContentList("드라마", dramaList, navController)
}

@Composable
fun ContentList(title: String, list: List<Content>, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Spacer(Modifier.height(40.dp))
        Text(title, color = Color.White, fontSize = 22.sp, modifier = Modifier.padding(16.dp))
        LazyColumn {
            items(list) {
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clickable { navController.navigate("detail/${it.id}") },
                    colors = CardDefaults.cardColors(containerColor = it.color),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(it.title, modifier = Modifier.padding(24.dp), fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun DetailScreen(item: Content, navController: NavHostController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Brush.verticalGradient(listOf(item.color, Color.Black))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(20.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(20.dp))
        AnimatedVisibility(visible = visible, enter = slideInVertically { it / 2 } + fadeIn()) {
            Card(
                modifier = Modifier.padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.4f))
            ) {
                Text(
                    text = item.quote,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
        Spacer(Modifier.height(30.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("정보", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    "• 감독: ${item.director}\n• 제작비: ${item.budget}\n• 시청률: ${item.rating}",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }
        Spacer(Modifier.height(40.dp))
        Button(onClick = { navController.popBackStack() }) { Text("뒤로가기") }
        Spacer(Modifier.height(40.dp))
    }
}
