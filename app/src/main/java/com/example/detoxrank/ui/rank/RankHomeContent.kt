package com.example.detoxrank.ui.rank

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.user.Rank
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.theme.rank_color
import com.example.detoxrank.ui.theme.rank_color_ultra_dark
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankHomeScreen(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    detoxRankViewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    if (navigationType == DetoxRankNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(modifier.width(240.dp)) {
                NavigationDrawerContent(
                    selectedDestination = detoxRankUiState.currentSection,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList
                )
            }
        }
        ) {
            RankContent(
                navigationItemContentList = navigationItemContentList,
                detoxRankUiState = detoxRankUiState,
                onTabPressed = onTabPressed,
                navigationType = navigationType,
                detoxRankViewModel = detoxRankViewModel
            )
        }
    } else {
        RankContent(
            navigationItemContentList = navigationItemContentList,
            detoxRankUiState = detoxRankUiState,
            onTabPressed = onTabPressed,
            navigationType = navigationType,
            detoxRankViewModel = detoxRankViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankContent(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankViewModel: DetoxRankViewModel,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    modifier: Modifier = Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = detoxRankViewModel.getLevelProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Row(modifier = modifier.fillMaxSize()) {
        // navigation rail (side)
        AnimatedVisibility(
            visible = navigationType == DetoxRankNavigationType.NAVIGATION_RAIL
        ) {
            DetoxRankNavigationRail(
                currentTab = detoxRankUiState.currentSection,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList
            )
        }
        Scaffold(
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp, start = 15.dp)
                ) {
                    Box {
                        Image(painterResource(R.drawable.brain_enlighted), null, modifier = Modifier.width(40.dp))
                        Text("1", style = MaterialTheme.typography.headlineLarge)
                    }
                    LinearProgressIndicator(
                        progress = animatedProgress,
                        color = MaterialTheme.colorScheme.tertiary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .height(12.dp)
                            .padding(start = 5.dp, end = 16.dp, top = 0.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary), RoundedCornerShape(5.dp))
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.weight(0.6f))
                }
            },
            bottomBar = {
                if (navigationType == DetoxRankNavigationType.BOTTOM_NAVIGATION)
                    DetoxRankBottomNavigationBar(
                        currentTab = detoxRankUiState.currentSection,
                        onTabPressed = onTabPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
            }
        ) { paddingValues ->
            // keep everything centered when on mobile screen size
            if (navigationType == DetoxRankNavigationType.BOTTOM_NAVIGATION) {
                RankMainScreenBody(detoxRankViewModel = detoxRankViewModel, modifier = Modifier.padding(paddingValues))
            } else {
                RankMainScreenBody(detoxRankViewModel = detoxRankViewModel, modifier = Modifier.padding(paddingValues)) // TODO change layout
            }

        }
    }
}

@Composable
fun RankMainScreenBody(
    detoxRankViewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    var currRankPair: Pair<Rank, Pair<Int, Int>>

    var currentRank by remember { mutableStateOf(Rank.Bronze1) }

    LaunchedEffect(Unit) {
        val rankPoints = detoxRankViewModel.getUserRankPoints()
        currRankPair = detoxRankViewModel.getCurrentRank(rankPoints)
        currentRank = currRankPair.first

        detoxRankViewModel.setRankProgressBar((rankPoints - currRankPair.second.first).toFloat() / (currRankPair.second.second - currRankPair.second.first))
    }

    val animatedProgress = animateFloatAsState(
        targetValue = detoxRankViewModel.getRankProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painterResource(id =
                getRankDrawableId(currentRank)
            ),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 35.dp, start = 16.dp, end = 16.dp)
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            color = rank_color,
            trackColor = rank_color_ultra_dark,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(30.dp)
                .padding(start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(BorderStroke(4.dp, rank_color), RoundedCornerShape(10.dp))
        )
        Button(
            onClick = {
                /* TODO display lazy vertical grid of achievements */
            },
            modifier = Modifier.padding(top = 135.dp).fillMaxWidth(0.7f)
        ) {
            Text(
                "ACHIEVEMENTS",
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
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