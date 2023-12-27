package com.example.gametask.ui.endgamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gametask.CoinViewModel
import com.example.gametask.R
import com.example.gametask.ui.navigation.Screen
import com.example.gametask.ui.theme.DarkGray
import com.example.gametask.ui.theme.GrayColor
import com.example.gametask.ui.theme.Purple

@Composable
fun EndGameScreen(navController: NavController, coinViewModel: CoinViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments
    val earnedCoins = arguments?.getInt("earnedCoins") ?: 0
    coinViewModel.updateCoins(coinViewModel.coins.value + earnedCoins)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TrophyIconAndText()
        Spacer(modifier = Modifier.size(20.dp))
        RewardBox(earnedCoins)
        Spacer(modifier = Modifier.size(40.dp))
        BottomRow{
            navController.navigate(Screen.MenuScreen.route)
        }
    }
}

@Composable
private fun TrophyIconAndText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_trophy),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "[CONGRATULATIONS!]",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "[Great! You won!]",
            fontSize = 20.sp
        )
    }

}

@Composable
private fun DoubleRewardButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(width = 250.dp, height = 75.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Purple),
    ) {
        Text(
            text = "Double Reward",
            fontSize = 24.sp
        )
    }
}

@Composable
private fun BackHomeButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(75.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(GrayColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = Icons.Filled.Home,
            modifier = Modifier.size(75.dp),
            contentDescription = null
        )
    }
    Spacer(modifier = Modifier.size(25.dp))
}

@Composable
private fun BottomRow(onClick: () -> Unit) {
    Row {
        DoubleRewardButton()
        Spacer(modifier = Modifier.size(10.dp))
        BackHomeButton(onClick)
    }
}

@Composable
private fun RewardBox(earnedCoins: Int) {
    Box(
        modifier = Modifier
            .size(width = 300.dp, height = 75.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_coin),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = earnedCoins.toString(),
                fontSize = 20.sp
            )
        }
    }
}