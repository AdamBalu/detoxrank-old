package com.example.detoxrank.ui.tasks.home

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.tasks.task.TaskList
import com.example.detoxrank.ui.tasks.task.TaskViewModel
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksHomeScreen(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    modifier: Modifier = Modifier
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
            TasksContent(
                navigationItemContentList = navigationItemContentList,
                detoxRankUiState = detoxRankUiState,
                onTabPressed = onTabPressed,
                navigationType = navigationType
            )
        }
    } else {
        TasksContent(
            navigationItemContentList = navigationItemContentList,
            detoxRankUiState = detoxRankUiState,
            onTabPressed = onTabPressed,
            navigationType = navigationType
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksContent(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    modifier: Modifier = Modifier,
    viewModel: TasksHomeViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory),
    taskViewModel: TaskViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory)
) {
    val tasksHomeUiState by viewModel.tasksHomeUiState.collectAsState()

//    val tasksToAdd = LocalTasksDataProvider.tasks

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
//        CreateTaskMenu(taskViewModel = taskViewModel, modifier = Modifier.padding(30.dp)) // DATA remove prob
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    viewModel.invertCreateTaskMenuShownValue()
                }) {
                    Icon(
                        imageVector = Icons.Filled.AddTask,
                        contentDescription = stringResource(id = R.string.add_task)
                    )
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Tasks",
                            style = Typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            fontSize = 35.sp
                        )
                    }
                )
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
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxHeight()
                        .zIndex(1f)
                        // blocks simultaneous taps under the box
                        .pointerInput(Unit) { detectTapGestures(onTap = {}) }
                ) {
                    CreateTaskMenu(
                        viewModel = viewModel,
                        taskViewModel = taskViewModel,
                        modifier = Modifier
                            .fillMaxHeight(0.75f)
                            .padding(start = 25.dp, end = 25.dp)
                    )
                }
                TaskList(
                    taskList = tasksHomeUiState.taskList,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                )
            } else {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxHeight()
                        .zIndex(1f)
                        // blocks simultaneous taps under the box
                        .pointerInput(Unit) { detectTapGestures(onTap = {}) }
                ) {
                    CreateTaskMenu(
                        viewModel = viewModel,
                        taskViewModel = taskViewModel,
                        modifier = Modifier
                            .fillMaxHeight(0.75f)
                            .padding(start = 35.dp, end = 35.dp)
                    )
                }
                TaskList(
                    taskList = tasksHomeUiState.taskList,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                ) // TODO change layout
            }

        }
    }
}

@Composable
fun TasksHeading(
    @StringRes headingRes: Int,
    timeLeft: Int,
    iconImageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp, bottom = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(headingRes),
                style = Typography.bodyLarge,
                modifier = Modifier.padding(end = 10.dp)
            )
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }
//        Text( TODO
//            stringResource(id = R.string.tasklist_time_left, timeLeft)
//        )
    }
}
