package com.example.detoxrank.ui.rank

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.data.Section
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.utils.AnimationBox
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

@ExperimentalMaterial3Api
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
    LaunchedEffect(Unit) {
        rankViewModel.setLocalRankPoints()
        val rankPoints = detoxRankViewModel.getUserRankPoints()
        val currRankPair = detoxRankViewModel.getCurrentRank(rankPoints)
        val currentRank = currRankPair.first
        rankViewModel.setLocalRankBounds(currRankPair.second)
        detoxRankViewModel.setCurrentRank(currentRank)
        detoxRankViewModel.setRankProgressBar((rankPoints - currRankPair.second.first).toFloat() / (currRankPair.second.second - currRankPair.second.first))
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .zIndex(1f)) {
        AchievementsScreen(detoxRankViewModel = detoxRankViewModel, rankViewModel = rankViewModel)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxSize()
    ) {
        AnimationBox(enter = slideInVertically() {x -> x / 40} + fadeIn()) {
            RankWithProgressBar(
                detoxRankViewModel = detoxRankViewModel,
                rankViewModel = rankViewModel
            )
        }

        AnimationBox(enter = slideInVertically() { x -> x / 2 }) {
            Button(
                onClick = {
                    rankViewModel.setAchievementsDisplayed(true)
                },
                shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(0.75f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 15.dp, top = 10.dp),
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                    Text(
                        "ACHIEVEMENTS",
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
        }
    }
}