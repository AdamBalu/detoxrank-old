package com.example.detoxrank.ui.tasks.home

import android.content.Context
import android.os.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.local.LocalTasksDataProvider
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.service.TimerService
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.tasks.task.TaskList
import com.example.detoxrank.ui.tasks.task.TaskViewModel
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.utils.DetoxRankNavigationType
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TasksHomeScreen(
    navigationItemContentList: List<NavigationItemContent>,
    timerService: TimerService,
    detoxRankUiState: DetoxRankUiState,
    detoxRankViewModel: DetoxRankViewModel,
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
                timerService = timerService,
                detoxRankUiState = detoxRankUiState,
                detoxRankViewModel = detoxRankViewModel,
                onTabPressed = onTabPressed,
                navigationType = navigationType
            )
        }
    } else {
        TasksContent(
            navigationItemContentList = navigationItemContentList,
            timerService = timerService,
            detoxRankUiState = detoxRankUiState,
            detoxRankViewModel = detoxRankViewModel,
            onTabPressed = onTabPressed,
            navigationType = navigationType
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TasksContent(
    navigationItemContentList: List<NavigationItemContent>,
    timerService: TimerService,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    detoxRankViewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier,
    viewModel: TasksHomeViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory),
    taskViewModel: TaskViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory)
) {
    val tasksHomeUiState by viewModel.tasksHomeUiState.collectAsState()

    val tasksToAdd = LocalTasksDataProvider.tasks
    val coroutineScope = rememberCoroutineScope()

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
            floatingActionButton = {
                FloatingActionButton(onClick = {
//                    coroutineScope.launch { // DATA uncomment to fill task db
//                        tasksToAdd.forEach {
//                            taskViewModel.updateUiState(it.toTaskUiState())
//                            taskViewModel.insertTaskToDatabase()
//                        }
//                    }
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
                    timerService = timerService,
                    taskList = tasksHomeUiState.taskList,
                    detoxRankViewModel = detoxRankViewModel,
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
                    timerService = timerService,
                    taskList = tasksHomeUiState.taskList,
                    detoxRankViewModel = detoxRankViewModel,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                ) // TODO change layout
            }

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TasksHeading(
    @StringRes headingRes: Int,
    timerService: TimerService,
    category: TaskDurationCategory,
    iconImageVector: ImageVector,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    var isLaunched by remember { mutableStateOf(false) }
    val nextMonth = Calendar.getInstance()
    nextMonth.set(Calendar.DAY_OF_MONTH, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH))
    nextMonth.set(Calendar.HOUR_OF_DAY, 23)
    nextMonth.set(Calendar.MINUTE, 59)
    nextMonth.set(Calendar.SECOND, 59)

    val midnight = Calendar.getInstance()
    midnight.set(Calendar.HOUR_OF_DAY, 23)
    midnight.set(Calendar.MINUTE, 59)
    midnight.set(Calendar.SECOND, 59)

    val currentTimeMillis = System.currentTimeMillis()

    taskViewModel.checkNewMonthTasksTest()

    val context = LocalContext.current // DATA add back later
    val prefs = remember { context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE) }
    val isFirstLaunch = remember { prefs.getBoolean("first_run_tasks_v1", true) }
    if (isFirstLaunch) {
        prefs.edit().putBoolean("first_run_tasks_v1", false).apply()
        taskViewModel.getNewTasks()
        taskViewModel.isShown.value = true
    }

    val timeDiff = midnight.timeInMillis - currentTimeMillis

    val secondsLeft = timeDiff / 1000
    val daysToNextMonth = (nextMonth.timeInMillis - currentTimeMillis) / 1000 / 60 / 60 / 24

    val timeLeftDays = daysToNextMonth.days
    val timeLeftHours = ((secondsLeft / 60 / 60) % 24).hours
    val timeLeftMinutes = ((secondsLeft / 60 ) % 60).minutes
    val timeLeftSeconds = ((secondsLeft) % 60).seconds

    if (!isLaunched) {
        timerService.disableTaskTimer()
        timerService.initTaskTimer()
        isLaunched = true
    }

    val taskDuration = timeLeftDays + timeLeftHours + timeLeftMinutes + timeLeftSeconds
    timerService.setTaskDuration(taskDuration)


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TaskTimer(category, timerService)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TaskTimer(
    category: TaskDurationCategory,
    timerService: TimerService,
    modifier: Modifier = Modifier
) {
    val daysRemainingWeek = 7 - Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    var isVisible by remember { mutableStateOf(false) }
    val hoursRemaining by timerService.hoursDay
    val minutesRemaining by timerService.minutesDay
    val secondsRemaining by timerService.secondsDay

    val daysRemainingMonth by timerService.daysMonth

    val nextWeek = Calendar.getInstance()
    nextWeek.set(Calendar.DAY_OF_MONTH, nextWeek.getActualMaximum(Calendar.DAY_OF_WEEK))
    nextWeek.set(Calendar.HOUR_OF_DAY, 23)
    nextWeek.set(Calendar.MINUTE, 59)
    nextWeek.set(Calendar.SECOND, 59)

    LaunchedEffect(Unit) {
        delay(800)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(tween(700, easing = LinearOutSlowInEasing)) { height -> height/5 }
    ) {
        when (category) {
            TaskDurationCategory.Daily -> {
                Text(
                    stringResource(id = R.string.tasklist_time_left,
                        "${hoursRemaining}h ${minutesRemaining}min ${secondsRemaining}s"
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TaskDurationCategory.Weekly -> {
                val formattedTimer = if (daysRemainingWeek > 1) {
                    "${daysRemainingWeek}d ${hoursRemaining}h"
                } else {
                    "${hoursRemaining}h ${minutesRemaining}min ${secondsRemaining}s"
                }
                Text(
                    stringResource(id = R.string.tasklist_time_left, formattedTimer),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TaskDurationCategory.Monthly -> {
                val daysRemainingMonthInt = daysRemainingMonth.toInt()
                val formattedTimer = if (daysRemainingMonthInt > 7) "${daysRemainingMonth}d"
                    else if (daysRemainingMonthInt > 1) "${daysRemainingMonth}d ${hoursRemaining}h"
                    else "${hoursRemaining}h ${minutesRemaining}min ${secondsRemaining}s"

                Text(
                    stringResource(id = R.string.tasklist_time_left, formattedTimer),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> { }
        }
    }
}
