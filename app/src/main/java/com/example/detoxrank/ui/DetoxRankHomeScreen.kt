package com.example.detoxrank.ui

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.service.TimerService
import com.example.detoxrank.ui.rank.AchievementViewModel
import com.example.detoxrank.ui.rank.RankHomeScreen
import com.example.detoxrank.ui.tasks.home.TasksHomeScreen
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.theory.TheoryHomeScreen
import com.example.detoxrank.ui.timer.TimerHomeScreen
import com.example.detoxrank.ui.utils.DetoxRankNavigationType
import com.example.detoxrank.ui.utils.getCurrentLevelFromXP
import com.example.detoxrank.ui.utils.getCurrentProgressBarProgression
import com.example.detoxrank.ui.utils.getLevelDrawableId
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun DetoxRankAppContent(
    windowSize: WindowWidthSizeClass,
    timerService: TimerService,
    modifier: Modifier = Modifier,
    viewModel: DetoxRankViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory),
    achievementViewModel: AchievementViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory)
) {
    val detoxRankUiState = viewModel.uiState.collectAsState().value
    val onTabPressed = { section: Section -> viewModel.updateCurrentSection(section = section) } // TODO reset home screen states if needed

    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            DetoxRankNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            DetoxRankNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            DetoxRankNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            DetoxRankNavigationType.BOTTOM_NAVIGATION
        }
    }

    val navigationItemContentList = listOf(
        NavigationItemContent(
            section = Section.Rank,
            icon = Icons.Filled.LocalPolice,
            text = stringResource(id = R.string.tab_rank),
            tint = rank_color
        ),
        NavigationItemContent(
            section = Section.Tasks,
            icon = Icons.Filled.Checklist,
            text = stringResource(id = R.string.tab_tasks),
            tint = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary
        ),
        NavigationItemContent(
            section = Section.Timer,
            icon = Icons.Filled.AvTimer,
            text = stringResource(id = R.string.tab_timer),
            tint = if (isSystemInDarkTheme()) timer_color_dark else md_theme_light_error
        ),
        NavigationItemContent(
            section = Section.Theory,
            icon = Icons.Filled.HistoryEdu,
            text = stringResource(id = R.string.tab_theory),
            tint = if (isSystemInDarkTheme()) md_theme_dark_primary else md_theme_light_primary
        )
    )

    when (detoxRankUiState.currentSection) {
        Section.Rank -> {
            RankHomeScreen(
                navigationItemContentList = navigationItemContentList,
                onTabPressed = onTabPressed,
                navigationType = navigationType,
                detoxRankUiState = detoxRankUiState,
                achievementViewModel = achievementViewModel,
                detoxRankViewModel = viewModel
            )
        }
        Section.Tasks -> {
            TasksHomeScreen(
                modifier = modifier,
                timerService = timerService,
                detoxRankUiState = detoxRankUiState,
                detoxRankViewModel = viewModel,
                achievementViewModel = achievementViewModel,
                navigationType = navigationType,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList
            )
        }
        Section.Timer -> {
            TimerHomeScreen(
                timerService = timerService,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                navigationType = navigationType,
                detoxRankUiState = detoxRankUiState,
                detoxRankViewModel = viewModel,
                achievementViewModel = achievementViewModel
            )
        }
        Section.Theory -> {
            TheoryHomeScreen(
                modifier = modifier,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                navigationType = navigationType,
                detoxRankUiState = detoxRankUiState,
                detoxRankViewModel = viewModel
            )
        }
    }
}

@Composable
fun DetoxRankBottomNavigationBar(
    currentTab: Section,
    onTabPressed: ((Section) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.section,
                onClick = { onTabPressed(navItem.section) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                        tint = navItem.tint
                    )
                }
            )
        }
    }
}

/**
 * Component that displays Navigation Rail
 */
@Composable
fun DetoxRankNavigationRail(
    currentTab: Section,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
    onTabPressed: ((Section) -> Unit) = {}
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.section,
                onClick = { onTabPressed(navItem.section) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text,
                        tint = navItem.tint
                    )
                }
            )
        }
    }
}

/**
 * Component that displays Navigation Drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    selectedDestination: Section,
    onTabPressed: ((Section) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(12.dp)
    ) {
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.section,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text,
                        tint = navItem.tint
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.section) }
            )
        }
    }
}

@Composable
fun DetoxRankTopAppBar(
    detoxRankViewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    val currentLevel = detoxRankViewModel.getCurrentLevel()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val xpPoints = detoxRankViewModel.getUserXPPoints()
        Log.d("XPPoints", "$xpPoints")
        val currentLevelToUpdate = getCurrentLevelFromXP(xpPoints = xpPoints)
        detoxRankViewModel.setCurrentLevel(currentLevelToUpdate)

        val progress = getCurrentProgressBarProgression(xpPoints)
        detoxRankViewModel.setLevelProgressBar(progress)
    }

    val animatedProgress = animateFloatAsState(
        targetValue = detoxRankViewModel.getLevelProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    val levelBadgeSize: Dp
    val xpBarPaddingStart: Dp
    val xpBarPaddingTop: Dp
    val xpBarHeight: Dp
    when (currentLevel) {
        in 0..14 -> {
            levelBadgeSize = 42.dp
            xpBarPaddingStart = 32.dp
            xpBarPaddingTop = 12.dp
            xpBarHeight = 25.dp
        }
        in 15..25 -> {
            levelBadgeSize = 65.dp
            xpBarPaddingStart = 45.dp
            xpBarPaddingTop = 25.dp
            xpBarHeight = 40.dp
        }
        else -> {
            levelBadgeSize = 40.dp
            xpBarPaddingStart = 30.dp
            xpBarPaddingTop = 25.dp
            xpBarHeight = 45.dp
        }
    }
    Row {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.padding(top = 12.dp, start = 20.dp)
        ) {
            Box {
                Image(
                    painterResource(getLevelDrawableId(currentLevel)),
                    null,
                    modifier = Modifier
                        .size(levelBadgeSize)
                        .zIndex(1f)
                )

                if (currentLevel != 25) {
                    LinearProgressIndicator(
                        progress = animatedProgress,
                        color = MaterialTheme.colorScheme.tertiary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .height(xpBarHeight)
                            .padding(start = xpBarPaddingStart, end = 16.dp, top = xpBarPaddingTop)
                            .fillMaxWidth(0.35f)
                            .clip(RoundedCornerShape(2.dp))
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                                RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        }
        Button(onClick = {
            coroutineScope.launch {
                detoxRankViewModel.updateUserRankPoints(500)
                detoxRankViewModel.updateUserXPPoints(1000)
            } },
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Get Rank+XP (dev)", style = Typography.bodySmall, fontSize = 10.sp)
        }

    }
}

data class NavigationItemContent(
    val section: Section,
    val icon: ImageVector,
    val text: String,
    val tint: Color
)