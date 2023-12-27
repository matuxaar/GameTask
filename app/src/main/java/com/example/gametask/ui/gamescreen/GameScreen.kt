package com.example.gametask.ui.gamescreen

import GameViewModel
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gametask.R
import com.example.gametask.ui.navigation.Screen
import com.example.gametask.ui.theme.GrayColor
import kotlinx.coroutines.delay

@Preview
@Composable
fun GameScreen(navController: NavController) {
    val gameViewModel: GameViewModel = viewModel()
    val remainingTime by gameViewModel.remainingTime
    val currentCoins by gameViewModel.currentCoins
    val isTimerRunning by gameViewModel.isTimerRunning
    val isGameOver by gameViewModel.isGameOver

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(40.dp))
        HeaderRow(remainingTime, currentCoins)
        ItemsList(
            isTimerRunning = isTimerRunning,
            isGameOver = isGameOver,
            gameViewModel = gameViewModel,
            navController = navController
        )
        BottomText()
    }
}

@Composable
private fun Timer(remainingTime: Int) {
    Row {
        Image(
            modifier = Modifier
                .size(40.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_timer),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 40.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(GrayColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = String.format(
                    "%02d:%02d",
                    remainingTime / 60, remainingTime % 60
                ),
                fontSize = 20.sp
            )
        }
    }
}


@Composable
private fun Coin(coinsEarned: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_coin),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = coinsEarned.toString(),
            fontSize = 20.sp
        )
    }
}

@Composable
private fun HeaderRow(remainingTime: Int, coinsEarned: Int) {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Timer(remainingTime = remainingTime)
        Coin(coinsEarned = coinsEarned)
    }
}


@Composable
private fun BottomText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Keep matching up two of the same\n" +
                    "object until there are no more to be\n" +
                    "paired and you clear the board"
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = "fast")
    }
}

@Composable
private fun GameCard(gameItem: GameItem, isOpen: Boolean, onItemClick: (GameItem) -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(80.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(GrayColor)
            .clickable { onItemClick(gameItem) },
        contentAlignment = Alignment.Center
    ) {
        if (isOpen) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = gameItem.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun ItemsList(
    isTimerRunning: Boolean,
    isGameOver: Boolean,
    gameViewModel: GameViewModel,
    navController: NavController
) {
    var openedCard by remember { mutableStateOf<GameItem?>(null) }
    var shouldCloseCards by remember { mutableStateOf(false) }
    var shouldResetCards by remember { mutableStateOf(false) }
    var pairedImages by remember { mutableStateOf(setOf<GameItem>()) }

    val shuffledItemList = remember {
        itemList.shuffled()
    }

    LaunchedEffect(shouldCloseCards) {
        if (shouldCloseCards) {
            delay(1000)
            openedCard = null
            shouldCloseCards = false
        }
    }

    LaunchedEffect(shouldResetCards) {
        if (shouldResetCards) {
            delay(1000)
            shouldResetCards = false
        }
    }

    DisposableEffect(gameViewModel) {
        onDispose {
            gameViewModel.stopTimer()
        }
    }

    if (!isTimerRunning) {
        gameViewModel.startTimer()
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(20.dp),
        columns = GridCells.Fixed(4),
        content = {
            items(shuffledItemList.size) {
                val gameItem = shuffledItemList[it]
                GameCard(
                    gameItem = gameItem,
                    isOpen = openedCard == gameItem || pairedImages.contains(gameItem)
                ) {
                    if (openedCard != null && openedCard!!.imageResId == gameItem.imageResId
                        && !pairedImages.contains(gameItem)
                    ) {
                        pairedImages += openedCard!!
                        pairedImages += gameItem
                        openedCard = null
                        shouldResetCards = true

                        if (pairedImages.size == shuffledItemList.size) {
                            val earnedCoins = gameViewModel.getEarnedCoins()
                            navController.navigate(Screen.EndGameScreen.route + "?earnedCoins=$earnedCoins")
                        }
                    } else if (!pairedImages.contains(gameItem)) {
                        openedCard = gameItem
                        shouldCloseCards = true
                    }
                }
            }
        }
    )
}

data class GameItem(val id: Int, val imageResId: Int)

private val itemList = listOf(
    GameItem(1, R.drawable.ic_cake),
    GameItem(2, R.drawable.ic_diamond),
    GameItem(3, R.drawable.ic_diamond_red),
    GameItem(4, R.drawable.ic_gem),
    GameItem(5, R.drawable.ic_gem2),
    GameItem(6, R.drawable.ic_gem3),
    GameItem(7, R.drawable.ic_leg),
    GameItem(8, R.drawable.ic_pizza),
    GameItem(9, R.drawable.ic_ring),
    GameItem(10, R.drawable.ic_tomato),
    GameItem(11, R.drawable.ic_cake),
    GameItem(12, R.drawable.ic_diamond),
    GameItem(13, R.drawable.ic_diamond_red),
    GameItem(14, R.drawable.ic_gem),
    GameItem(15, R.drawable.ic_gem2),
    GameItem(16, R.drawable.ic_gem3),
    GameItem(17, R.drawable.ic_leg),
    GameItem(18, R.drawable.ic_pizza),
    GameItem(19, R.drawable.ic_ring),
    GameItem(20, R.drawable.ic_tomato)
)

