package com.example.detoxrank.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.data.Section
import com.example.detoxrank.ui.tasks.TasksMainScreen
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.theory.TheoryMainScreen
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetoxRankHomeScreen(
    viewModel: DetoxRankViewModel,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    modifier: Modifier = Modifier
) {
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

    if (navigationType == DetoxRankNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(Modifier.width(240.dp)) {
                NavigationDrawerContent(
                    selectedDestination = detoxRankUiState.currentSection,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList
                )
            }
        }
        ) {
            DetoxRankContent(
                viewModel = viewModel,
                navigationType = navigationType,
                detoxRankUiState = detoxRankUiState,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier
            )
        }
    } else {
        DetoxRankContent(
            viewModel = viewModel,
            navigationType = navigationType,
            detoxRankUiState = detoxRankUiState,
            onTabPressed = onTabPressed,
            navigationItemContentList = navigationItemContentList,
            modifier = modifier
        )
    }

}

@Composable
private fun DetoxRankContent(
    viewModel: DetoxRankViewModel,
    navigationType: DetoxRankNavigationType,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier
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


        // keep everything centered when on mobile screen size
        if (navigationType == DetoxRankNavigationType.BOTTOM_NAVIGATION) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {

                // filler element for space evenly to work
                Spacer(modifier = Modifier.height(0.dp))

                DetoxRankMainScreenContent(
                    currentTab = detoxRankUiState.currentSection,
                    viewModel = viewModel,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                )

                // bottom navigation bar
                ReplyBottomNavigationBar(
                    currentTab = detoxRankUiState.currentSection,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.padding(bottom = 0.dp)
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                DetoxRankMainScreenContent(
                    currentTab = detoxRankUiState.currentSection,
                    viewModel = viewModel,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                )
                Spacer(modifier = Modifier.weight(1.0f)) // TODO just a filler to force navbar to go bottom
            }
        }

    }
}

@Composable
fun DetoxRankMainScreenContent(
    currentTab: Section,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier,
    onTabPressed: ((Section) -> Unit),
    navigationItemContentList: List<NavigationItemContent>
) {
    when (currentTab) {
        Section.Rank -> {

        }
        Section.Tasks -> {
            TasksMainScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        Section.Timer -> {

        }
        Section.Theory -> {
            TheoryMainScreen(
                modifier = modifier,
                viewModel = viewModel,
                currentTab = currentTab,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
            )
        }
    }

}

@Composable
fun ReplyBottomNavigationBar(
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
private fun DetoxRankNavigationRail(
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
private fun NavigationDrawerContent(
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