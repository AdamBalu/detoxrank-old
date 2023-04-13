package com.example.detoxrank.ui.timer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.data.TimerDifficultyCard
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.theme.Typography

@Composable
fun TimerDifficultySelectScreen(
    timerViewModel: TimerViewModel,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        timerViewModel.difficultySelectShown,
        enter = slideInVertically(animationSpec = tween(durationMillis = 500)) { height -> height } + fadeIn(
            animationSpec = tween(durationMillis = 500)
        ),
        exit = slideOutVertically(animationSpec = tween(durationMillis = 500)) { height -> height }
    ) {
        BackHandler {
            timerViewModel.setDifficultySelectShown(false)
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            items(timerViewModel.difficultyList) { card ->
                DifficultyCard(
                    card = card,
                    timerViewModel = timerViewModel,
                    viewModel = viewModel,
                    modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                )
            }
        }
    }
}

@Composable
fun DifficultyCard(
    card: TimerDifficultyCard,
    timerViewModel: TimerViewModel,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    val cardColor = when (card.difficulty) {
        TimerDifficulty.Easy -> MaterialTheme.colorScheme.tertiary
        TimerDifficulty.Medium -> MaterialTheme.colorScheme.primary
        TimerDifficulty.Hard -> MaterialTheme.colorScheme.error
    }

    Card(
        border = BorderStroke(4.dp, cardColor),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                timerViewModel.setDifficultySelectShown(false)
                viewModel.setTimerDifficulty(card.difficulty)
            }
            .padding(top = 7.dp)
            .height(card.height)
    ) {
        Box() {
            Image(
                painter = painterResource(id = card.backgroundImageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(5.dp)
            ) {
                Text(
                    text = card.difficulty.toString(),
                    style = Typography.headlineMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 15.dp, bottom = 5.dp, top = 5.dp),
                    color = cardColor
                )
                Card {
                    Column(
                        modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, top = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Avoid :",
                            style = Typography.bodySmall,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        for (item in card.avoidList) {
                            BannedItem(item)
                        }
                    }
                }
            }
        }
    }
}
