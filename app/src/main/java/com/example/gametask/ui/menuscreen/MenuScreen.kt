package com.example.gametask.ui.menuscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gametask.CoinViewModel
import com.example.gametask.R
import com.example.gametask.ui.navigation.Screen
import com.example.gametask.ui.theme.GrayColor
import com.example.gametask.ui.theme.Purple

@Preview
@Composable
fun MenuScreen(navController: NavController, coinViewModel: CoinViewModel) {
    val coins by coinViewModel.coins
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        ValueCoins(coins = coins)
        Spacer(modifier = Modifier.size(100.dp))
        MenuLogo()
        Spacer(modifier = Modifier.size(75.dp))
        PlayButton {
            navController.navigate(Screen.GameScreen.route)
        }
        Spacer(modifier = Modifier.size(100.dp))
        PrivacyIcon()
    }
}

@Composable
private fun MenuLogo() {
    Box(
        modifier = Modifier
            .size(300.dp)
            .clip(shape = RoundedCornerShape(45.dp))
            .background(GrayColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Game\nLogo №1",
            color = Color.Black,
            fontSize = 50.sp,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
private fun ValueCoins(coins: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_coin),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = coins.toString(),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.size(30.dp))
    }
}

@Composable
private fun PlayButton(onCLick: () -> Unit) {
    Button(
        onClick = { onCLick() },
        modifier = Modifier.width(150.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Purple),
    ) {
        Text(
            text = "PLAY",
            fontSize = 30.sp
        )
    }
}

@Composable
private fun PrivacyIcon() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(GrayColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_privacy),
                modifier = Modifier.size(75.dp),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(25.dp))
    }
}