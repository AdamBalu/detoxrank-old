package com.example.detoxrank.ui.tasks

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.Task
import com.example.detoxrank.data.TaskCategory
import com.example.detoxrank.data.local.LocalTasksDataProvider.tasks
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.theme.*
import com.example.detoxrank.ui.utils.AnimationBox
import com.example.detoxrank.ui.utils.RankPointsGain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksMainScreen(
    modifier: Modifier = Modifier,
    viewModel: DetoxRankViewModel = viewModel(),
    navigationItemContentList: List<NavigationItemContent>,
    currentTab: Section,
    onTabPressed: ((Section) -> Unit)
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
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
            DetoxRankBottomNavigationBar(
                currentTab = currentTab,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList
            )
        }
    ) { innerPadding ->
        TaskList(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
    
    
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    viewModel: DetoxRankViewModel = viewModel()
) {
    if (viewModel.taskList.all { it.completed.value }) {
        viewModel.cleanTaskList(tasks)
        viewModel.resetTaskCompletionValues(tasks)
        viewModel.fillTaskList(tasks)
    }
    if (viewModel.taskList.isEmpty()) {
        viewModel.resetTaskCompletionValues(tasks)
        viewModel.fillTaskList(tasks)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(top = 100.dp, start = 50.dp, end = 50.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.TaskAlt,
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier.size(250.dp),
                tint = if (isSystemInDarkTheme())
                    md_theme_dark_tertiary
                else
                    md_theme_light_tertiary
            )
            Text(
                stringResource(R.string.congratulations),
                style = Typography.headlineMedium,
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme())
                    md_theme_dark_tertiary
                else
                    md_theme_light_tertiary,
            )
            Text(
                stringResource(R.string.all_tasks_completed_description),
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ) {
            item {
                TasksHeading(
                    headingRes = R.string.tasklist_heading_daily,
                    timeLeft = 0 /* TODO */,
                    iconImageVector = Icons.Filled.Today
                )
            }
            items(viewModel.taskList.filter { it.category == TaskCategory.Daily }) { task ->
                AnimationBox {
                    Task(
                        task = task
                    )
                }
            }
            item {
                TasksHeading(
                    headingRes = R.string.tasklist_heading_weekly,
                    timeLeft = 0 /* TODO */,
                    iconImageVector = Icons.Filled.DateRange
                )
            }
            items(viewModel.taskList.filter { it.category == TaskCategory.Weekly }) { task ->
                AnimationBox {
                    Task(
                        task = task
                    )
                }
            }
            item {
                TasksHeading(
                    headingRes = R.string.tasklist_heading_monthly,
                    timeLeft = 0 /* TODO */,
                    iconImageVector = Icons.Filled.CalendarMonth
                )
            }
            items(viewModel.taskList.filter { it.category == TaskCategory.Monthly }) { task ->
                AnimationBox {
                    Task(
                        task = task
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.padding(bottom = 75.dp))
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

@Composable
fun Task(
    task: Task,
    modifier: Modifier = Modifier
) {
    val rankPointsGain = when (task.category) {
        TaskCategory.Daily -> 100
        TaskCategory.Weekly -> 300
        TaskCategory.Monthly -> 600
    }

    val darkTheme = isSystemInDarkTheme()

    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable { task.completed.value = !task.completed.value }
            .height(if (task.completed.value) IntrinsicSize.Min else IntrinsicSize.Max)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        colors = if (task.completed.value) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        } else {
            when (task.category) {
                TaskCategory.Daily ->
                    if (darkTheme)
                        CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary)
                    else
                        CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                TaskCategory.Weekly ->
                    if (darkTheme)
                        CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
                    else
                        CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
                TaskCategory.Monthly ->
                    if (darkTheme)
                        CardDefaults.cardColors(MaterialTheme.colorScheme.onError)
                    else
                        CardDefaults.cardColors(MaterialTheme.colorScheme.errorContainer)
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 10.dp,
                    top = if (task.completed.value) 2.dp else 18.dp,
                    bottom = if (task.completed.value) 2.dp else 14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.widthIn(0.dp, 290.dp)
            ) {
                Column {
                    Icon(
                        imageVector = task.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(start = 5.dp, end = 0.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    AnimatedVisibility(
                        visibleState = MutableTransitionState(!task.completed.value),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        RankPointsGain(
                            rankPointsGain = rankPointsGain,
                            horizontalArrangement = Arrangement.Center,
                            plusIconSize = 10.dp,
                            fontSize = 10.sp,
                            shieldIconSize = 11.dp
                        )
                    }
                }

                AnimatedVisibility(
                    visibleState = MutableTransitionState(!task.completed.value),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        Text(
                            text = stringResource(task.description),
                            style = Typography.bodyMedium,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visibleState = MutableTransitionState(task.completed.value),
                    enter = expandHorizontally() + fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = stringResource(R.string.task_completed),
                        style = Typography.bodyMedium,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(start = 38.dp)
                    )
                }

            }

            Checkbox(
                checked = task.completed.value,
                onCheckedChange = {
                    task.completed.value = !task.completed.value
                }
            )
        }
    }
}

@Preview
@Composable
fun TasksMainScreenPreview() {
    TasksMainScreen(
        navigationItemContentList = listOf(),
        currentTab = Section.Tasks,
        onTabPressed = {}
    )
}
