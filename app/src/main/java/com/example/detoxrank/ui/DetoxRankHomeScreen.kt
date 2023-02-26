package com.example.detoxrank.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.data.Section
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetoxRankHomeScreen(
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    modifier: Modifier = Modifier
) {
//    val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
    val navigationItemContentList = listOf(
        NavigationItemContent(
            section = Section.Rank,
            icon = Icons.Outlined.LocalPolice,
            text = stringResource(id = R.string.tab_rank)
        ),
        NavigationItemContent(
            section = Section.Tasks,
            icon = Icons.Outlined.Checklist,
            text = stringResource(id = R.string.tab_tasks)
        ),
        NavigationItemContent(
            section = Section.Timer,
            icon = Icons.Outlined.AvTimer,
            text = stringResource(id = R.string.tab_timer)
        ),
        NavigationItemContent(
            section = Section.Theory,
            icon = Icons.Outlined.HistoryEdu,
            text = stringResource(id = R.string.tab_theory)
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
                navigationType = navigationType,
                detoxRankUiState = detoxRankUiState,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier
            )
        }
    } else {
        DetoxRankContent(
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Spacer(modifier = Modifier.weight(1.0f)) // TODO just a filler to force navbar to go bottom
            // bottom navigation bar
            AnimatedVisibility(
                visible = navigationType == DetoxRankNavigationType.BOTTOM_NAVIGATION
            ) {
                ReplyBottomNavigationBar(
                    currentTab = detoxRankUiState.currentSection,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList
                )
            }
        }
    }
}

@Composable
private fun ReplyBottomNavigationBar(
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
                        contentDescription = navItem.text
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
                        contentDescription = navItem.text
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
    selectedDestination: Any,
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
                        contentDescription = navItem.text
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

private data class NavigationItemContent(
    val section: Section,
    val icon: ImageVector,
    val text: String
)