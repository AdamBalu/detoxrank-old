package com.example.detoxrank.ui.rank

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.detoxrank.R
import com.example.detoxrank.data.user.Rank
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.rank_color
import com.example.detoxrank.ui.theme.rank_color_shade
import com.example.detoxrank.ui.theme.rank_color_ultra_dark
import com.example.detoxrank.ui.utils.AnimationBox

@Composable
fun RankWithProgressBar(
    detoxRankViewModel: DetoxRankViewModel,
    rankViewModel: RankViewModel,
    modifier: Modifier = Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = detoxRankViewModel.getRankProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    val currentRank = detoxRankViewModel.getCurrentRank()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "RANK",
            textAlign = TextAlign.Center,
            style = Typography.bodySmall,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            color = MaterialTheme.colorScheme.outline,
            letterSpacing = 2.sp
        )
        Text(
            currentRank.rankName,
            textAlign = TextAlign.Center,
            style = Typography.headlineLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Image(
            painterResource(id = getRankDrawableId(currentRank)),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 25.dp, start = 16.dp, end = 16.dp)
        )
        Box {
            Text(
                "RANK POINTS (RP)",
                style = Typography.bodySmall,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.TopCenter),
                fontStyle = FontStyle.Normal
            )
            LinearProgressIndicator(
                progress = animatedProgress,
                color = rank_color,
                trackColor = rank_color_ultra_dark,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(47.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 17.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .border(BorderStroke(4.dp, rank_color), RoundedCornerShape(19.dp))
            )
            LinearProgressIndicator(
                progress = animatedProgress - 0.1f,
                color = rank_color_shade,
                trackColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(31.dp)
                    .padding(start = 30.dp, top = 23.dp)
                    .clip(RoundedCornerShape(29.dp))
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimationBox(
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = 700
                        )
                    ) { x -> x / 20 } + fadeIn(
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = 700
                        )
                    )
                ) {
                    Column {
                        Row(modifier = Modifier.padding(top = 50.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                rankViewModel.getFormattedRankPointsProgress(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Image(
                                painterResource(id = R.drawable.rank_points_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RankWithProgressBarLarge(
    detoxRankViewModel: DetoxRankViewModel,
    rankViewModel: RankViewModel,
    modifier: Modifier = Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = detoxRankViewModel.getRankProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    val currentRank = detoxRankViewModel.getCurrentRank()
    val rankImageWidth = minOf(LocalConfiguration.current.screenHeightDp / (1 + 0.9), LocalConfiguration.current.screenWidthDp / 2.0).toInt().dp

    Column(modifier = modifier.fillMaxWidth().padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Image(
                painterResource(id = getRankDrawableId(currentRank)),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 20.dp, start = 16.dp, end = 16.dp).width(rankImageWidth)
            )
            Column {
                Text(
                    "RANK",
                    textAlign = TextAlign.Center,
                    style = Typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colorScheme.outline,
                    letterSpacing = 2.sp
                )
                Text(
                    currentRank.rankName,
                    textAlign = TextAlign.Center,
                    style = Typography.headlineLarge
                )
            }
        }

        Box {
            Text(
                "RANK POINTS (RP)",
                style = Typography.bodySmall,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.TopCenter),
                fontStyle = FontStyle.Normal
            )
            LinearProgressIndicator(
                progress = animatedProgress,
                color = rank_color,
                trackColor = rank_color_ultra_dark,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(47.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 17.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .border(BorderStroke(4.dp, rank_color), RoundedCornerShape(19.dp))
            )
            LinearProgressIndicator(
                progress = animatedProgress - 0.1f,
                color = rank_color_shade,
                trackColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(31.dp)
                    .padding(start = 30.dp, top = 23.dp)
                    .clip(RoundedCornerShape(29.dp))
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimationBox(
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = 700
                        )
                    ) { x -> x / 20 } + fadeIn(
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = 700
                        )
                    )
                ) {
                    Column {
                        Row(modifier = Modifier.padding(top = 50.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                rankViewModel.getFormattedRankPointsProgress(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Image(
                                painterResource(id = R.drawable.rank_points_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

@DrawableRes
private fun getRankDrawableId(rank: Rank): Int {
    return when (rank) {
        Rank.Bronze1 -> R.drawable.bronze_1
        Rank.Bronze2 -> R.drawable.bronze_2
        Rank.Bronze3 -> R.drawable.bronze_3
        Rank.Silver1 -> R.drawable.silver_1
        Rank.Silver2 -> R.drawable.silver_2
        Rank.Silver3 -> R.drawable.silver_3
        Rank.Gold1 -> R.drawable.gold_1
        Rank.Gold2 -> R.drawable.gold_2
        Rank.Gold3 -> R.drawable.gold_3
        Rank.Platinum1 -> R.drawable.bronze_1
        Rank.Platinum2 -> R.drawable.bronze_1
        Rank.Platinum3 -> R.drawable.bronze_1
        Rank.Diamond1 -> R.drawable.bronze_1
        Rank.Diamond2 -> R.drawable.bronze_1
        Rank.Diamond3 -> R.drawable.bronze_1
        Rank.Master -> R.drawable.bronze_1
        Rank.Legend -> R.drawable.bronze_1
    }
}