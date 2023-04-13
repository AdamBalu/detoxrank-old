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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.user.Rank
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.theme.rank_color
import com.example.detoxrank.ui.theme.rank_color_shade
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
    modifier: Modifier = Modifier,
    rankViewModel: RankViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory)
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
                detoxRankViewModel = detoxRankViewModel,
                rankViewModel = rankViewModel
            )
        }
    } else {
        RankContent(
            navigationItemContentList = navigationItemContentList,
            detoxRankUiState = detoxRankUiState,
            onTabPressed = onTabPressed,
            navigationType = navigationType,
            detoxRankViewModel = detoxRankViewModel,
            rankViewModel = rankViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankContent(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankViewModel: DetoxRankViewModel,
    detoxRankUiState: DetoxRankUiState,
    rankViewModel: RankViewModel,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    modifier: Modifier = Modifier
) {
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
            topBar = { DetoxRankTopAppBar(detoxRankViewModel = detoxRankViewModel) },
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
                RankMainScreenBody(
                    detoxRankViewModel = detoxRankViewModel,
                    rankViewModel = rankViewModel,
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                RankMainScreenBody(
                    detoxRankViewModel = detoxRankViewModel,
                    rankViewModel = rankViewModel,
                    modifier = Modifier.padding(paddingValues)
                ) // TODO change layout
            }

        }
    }
}

@Composable
fun RankMainScreenBody(
    detoxRankViewModel: DetoxRankViewModel,
    rankViewModel: RankViewModel,
    modifier: Modifier = Modifier
) {
    var currRankPair: Pair<Rank, Pair<Int, Int>>

    var currentRank = detoxRankViewModel.getCurrentRank()

    LaunchedEffect(Unit) {
        val rankPoints = detoxRankViewModel.getUserRankPoints()
        currRankPair = detoxRankViewModel.getCurrentRank(rankPoints)
        currentRank = currRankPair.first
        detoxRankViewModel.setCurrentRank(currentRank)
        detoxRankViewModel.setRankProgressBar((rankPoints - currRankPair.second.first).toFloat() / (currRankPair.second.second - currRankPair.second.first))
    }

    val animatedProgress = animateFloatAsState(
        targetValue = detoxRankViewModel.getRankProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Box(modifier = Modifier.fillMaxSize().zIndex(1f)) {
        AchievementsScreen(detoxRankViewModel = detoxRankViewModel, rankViewModel = rankViewModel)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painterResource(id = getRankDrawableId(currentRank)),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 35.dp, start = 16.dp, end = 16.dp)
        )
        Box {
            LinearProgressIndicator(
                progress = animatedProgress,
                color = rank_color,
                trackColor = rank_color_ultra_dark,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(32.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .border(BorderStroke(4.dp, rank_color), RoundedCornerShape(19.dp))
            )
            LinearProgressIndicator(
                progress = animatedProgress - 0.1f,
                color = rank_color_shade,
                trackColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(16.dp)
                    .padding(start = 30.dp, top = 8.dp)
                    .clip(RoundedCornerShape(29.dp))
            )
        }
        Button(
            onClick = {
                rankViewModel.setAchievementsDisplayed(true)
            },
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(0.75f)
        ) {
            Text(
                "ACHIEVEMENTS",
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
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