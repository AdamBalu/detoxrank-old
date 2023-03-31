package com.example.detoxrank.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.service.TimerService
import com.example.detoxrank.ui.rank.RankHomeScreen
import com.example.detoxrank.ui.tasks.home.TasksHomeScreen
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.theory.TheoryHomeScreen
import com.example.detoxrank.ui.timer.TimerHomeScreen
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun DetoxRankAppContent(
    windowSize: WindowWidthSizeClass,
    timerService: TimerService,
    modifier: Modifier = Modifier,
    viewModel: DetoxRankViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory)
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
                detoxRankUiState = detoxRankUiState
            )
        }
        Section.Tasks -> {
            TasksHomeScreen(
                modifier = modifier,
                detoxRankUiState = detoxRankUiState,
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
                viewModel = viewModel
            )
        }
        Section.Theory -> {
            TheoryHomeScreen(
                modifier = modifier,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                navigationType = navigationType,
                detoxRankUiState = detoxRankUiState
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

data class NavigationItemContent(
    val section: Section,
    val icon: ImageVector,
    val text: String,
    val tint: Color
)