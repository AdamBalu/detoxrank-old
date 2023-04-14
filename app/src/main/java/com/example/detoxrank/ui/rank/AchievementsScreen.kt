package com.example.detoxrank.ui.rank

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.data.achievements.AchievementDifficulty
import com.example.detoxrank.data.local.LocalAchievementDataProvider
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.utils.getAchievementDrawableFromId

@Composable
fun AchievementsScreen(
    rankViewModel: RankViewModel,
    modifier: Modifier = Modifier
) {
    val achievementsToAdd = LocalAchievementDataProvider.allAchievements
    val coroutineScope = rememberCoroutineScope()

    val state by rankViewModel.achievementsHomeUiState.collectAsState()

    AnimatedVisibility(
        visible = rankViewModel.achievementsDisplayed.value,
        enter = slideInVertically(initialOffsetY = { 2 * (it / 2) }, animationSpec = tween(durationMillis = 600)),
        exit = slideOutVertically(targetOffsetY = {-it * 2}, animationSpec = tween(durationMillis = 600) {x -> -x})
    ) {
        BackHandler {
            rankViewModel.setAchievementsDisplayed(false)
        }
        Box(modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)) {
//            Button(onClick = {
//                coroutineScope.launch { // DATA uncomment to fill achievement db
//                    achievementsToAdd.forEach {
//                        detoxRankViewModel.updateAchievementUiState(it.toAchievementUiState())
//                        detoxRankViewModel.insertAchievementToDatabase()
//                    }
//                }
//            }) {
//                Text("Fill achievs db")
//            }
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .size(30.dp)
                        .clickable {
                            rankViewModel.setAchievementsDisplayed(false)
                        }
                )
                LazyColumn {
                    item {
                        Text(
                            "Achievements",
                            style = Typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 2.dp)
                        )
                    }
                    items(state.achievementList) { achievement ->
                        val cardBorderColor = when (achievement.difficulty) {
                            AchievementDifficulty.EASY -> common_green
                            AchievementDifficulty.MEDIUM -> rare_blue
                            AchievementDifficulty.HARD -> rarer_blue
                            AchievementDifficulty.VERY_HARD -> epic_purple
                            AchievementDifficulty.INSANE -> very_epic_magenta
                            AchievementDifficulty.LEGENDARY -> legendary_orange
                        }
                        Card(
                            elevation = cardElevation(8.dp),
                            colors = elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = if (achievement.achieved) {
                                BorderStroke(2.dp, cardBorderColor)
                            } else { BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceVariant) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        ) {
                            Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
                                if (!achievement.achieved) {
                                    Box(modifier = Modifier.padding(10.dp)) {
                                        Image(
                                            painterResource(id = getAchievementDrawableFromId(99)),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(Color.Gray),
                                            modifier = Modifier.size(80.dp)
                                        )
                                        Image(
                                            painterResource(id = R.drawable.brain),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(Color.Gray),
                                            modifier = Modifier.size(40.dp),
                                            alignment = Alignment.Center
                                        )
                                    }
                                } else {
                                    Image(
                                        painterResource(id = getAchievementDrawableFromId(99)),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(90.dp)
                                            .padding(start = 10.dp, top = 5.dp)
                                    )
                                }
                                Column(modifier = Modifier.padding(end = 10.dp, bottom = 6.dp, start = 10.dp)) {
                                    Text(achievement.name, style = Typography.headlineSmall, modifier = Modifier.padding(bottom = 7.dp))
                                    Text(achievement.description, style = Typography.bodyMedium)
                                }
                            }
                        }
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}