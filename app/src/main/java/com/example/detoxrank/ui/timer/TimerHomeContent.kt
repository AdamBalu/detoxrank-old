package com.example.detoxrank.ui.timer

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.R
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.service.ServiceHelper
import com.example.detoxrank.service.TimerService
import com.example.detoxrank.service.TimerState
import com.example.detoxrank.ui.*
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.rank_color
import com.example.detoxrank.ui.theme.rank_color_ultra_dark
import com.example.detoxrank.ui.utils.AnimationBox
import com.example.detoxrank.ui.utils.Constants.ACTION_SERVICE_CANCEL
import com.example.detoxrank.ui.utils.Constants.ACTION_SERVICE_START
import com.example.detoxrank.ui.utils.DetoxRankNavigationType
import com.hitanshudhawan.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun TimerHomeScreen(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankUiState: DetoxRankUiState,
    detoxRankViewModel: DetoxRankViewModel,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    timerService: TimerService,
    viewModel: DetoxRankViewModel,
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
            TimerContent(
                navigationItemContentList = navigationItemContentList,
                detoxRankUiState = detoxRankUiState,
                detoxRankViewModel = detoxRankViewModel,
                onTabPressed = onTabPressed,
                navigationType = navigationType,
                timerService = timerService,
                viewModel = viewModel
            )
        }
    } else {
        TimerContent(
            navigationItemContentList = navigationItemContentList,
            detoxRankUiState = detoxRankUiState,
            detoxRankViewModel = detoxRankViewModel,
            onTabPressed = onTabPressed,
            navigationType = navigationType,
            timerService = timerService,
            viewModel = viewModel
        )
    }
}
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun TimerContent(
    navigationItemContentList: List<NavigationItemContent>,
    detoxRankViewModel: DetoxRankViewModel,
    detoxRankUiState: DetoxRankUiState,
    onTabPressed: ((Section) -> Unit),
    navigationType: DetoxRankNavigationType,
    timerService: TimerService,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier,
    timerViewModel: TimerViewModel = viewModel(factory = DetoxRankViewModelProvider.Factory)
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
                    AnimatedVisibility(
                        !timerViewModel.difficultySelectShown,
                        enter = slideInVertically(animationSpec = tween(durationMillis = 500, delayMillis = 500)) { height -> height } + fadeIn(
                            animationSpec = tween(durationMillis = 700)
                        ),
                        exit = slideOutVertically(animationSpec = tween(durationMillis = 500)) { height -> height }
                    ) {
                        DetoxRankBottomNavigationBar(
                            currentTab = detoxRankUiState.currentSection,
                            onTabPressed = onTabPressed,
                            navigationItemContentList = navigationItemContentList
                        )
                    }
            }
        ) { paddingValues ->
            // keep everything centered when on mobile screen size
            if (navigationType == DetoxRankNavigationType.BOTTOM_NAVIGATION) {
                TimerBody(
                    timerService = timerService,
                    detoxRankUiState = detoxRankUiState,
                    timerViewModel = timerViewModel,
                    detoxRankViewModel = detoxRankViewModel,
                    viewModel = viewModel,
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                TimerBody(
                    timerService = timerService,
                    detoxRankUiState = detoxRankUiState,
                    timerViewModel = timerViewModel,
                    detoxRankViewModel = detoxRankViewModel,
                    viewModel = viewModel,
                    modifier = Modifier.padding(paddingValues)
                ) // TODO change layout
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun TimerBody(
    timerService: TimerService,
    detoxRankUiState: DetoxRankUiState,
    detoxRankViewModel: DetoxRankViewModel,
    timerViewModel: TimerViewModel,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.zIndex(1f)
    ) {
        TimerDifficultySelectScreen(
            timerViewModel = timerViewModel,
            viewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AnimationBox(
            enter = expandVertically(animationSpec = tween(durationMillis = 700)) +
                    fadeIn(animationSpec = tween(durationMillis = 1200))) {
            Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.height(350.dp)) {
                TimerClock(timerService)
                TimerStartStopButton(
                    timerService = timerService,
                    detoxRankViewModel = detoxRankViewModel,
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, top = 150.dp)
                        .align(Alignment.Center)
                )
            }
        }
        AnimationBox {
            TimerFooter(
                timerService = timerService,
                detoxRankUiState = detoxRankUiState,
                viewModel = viewModel,
                timerViewModel = timerViewModel,
                modifier = Modifier.padding(top = 50.dp)
            )
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun TimerFooter(
    timerService: TimerService,
    detoxRankUiState: DetoxRankUiState,
    viewModel: DetoxRankViewModel,
    timerViewModel: TimerViewModel,
    modifier: Modifier = Modifier
) {
    val days by timerService.days
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 35.dp, end = 35.dp, bottom = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "DAY STREAK",
                style = Typography.bodySmall
            )
            Text(
                "$days",
                style = Typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontSize = 43.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "DIFFICULTY",
                style = Typography.bodySmall
            )
            DifficultySelect(
                onClick = { timerViewModel.setDifficultySelectShown(true) },
                timerService = timerService,
                detoxRankUiState = detoxRankUiState
            )
        }
    }
}

@Composable
fun BannedItem(
    @StringRes item: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowRight,
            contentDescription = null,
            modifier = modifier.width(12.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            stringResource(item),
            style = Typography.bodySmall,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(bottom = 4.dp, start = 5.dp, end = 8.dp)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerClock(
    timerService: TimerService,
    modifier: Modifier = Modifier
) {
    val progressSeconds by animateFloatAsState(
        targetValue = timerService.seconds.value.toFloat() * calculateTimerFloatAddition(50f, 60)
    )
    val progressMinutes by animateFloatAsState(
        targetValue = timerService.minutes.value.toFloat() * calculateTimerFloatAddition(39f, 60))
    val progressHours by animateFloatAsState(
        targetValue = timerService.hours.value.toFloat() * calculateTimerFloatAddition(19.44f, 24)
    )

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            CircularProgressBar(
                modifier = Modifier
                    .width(328.dp)
                    .align(Alignment.Center),
                progress = progressSeconds,
                progressMax = 100f,
                progressBarColor =
                MaterialTheme.colorScheme.primary,
                progressBarWidth = 18.dp,
                backgroundProgressBarColor = Color.Transparent,
                backgroundProgressBarWidth = 1.dp,
                roundBorder = true,
                startAngle = 270f
            )
            CircularProgressBar(
                modifier = Modifier
                    .width(314.dp)
                    .align(Alignment.Center),
                progress = 50f,
                progressMax = 100f,
                progressBarColor =
                MaterialTheme.colorScheme.primary,
                progressBarWidth = 4.dp,
                backgroundProgressBarColor = Color.Transparent,
                backgroundProgressBarWidth = 1.dp,
                roundBorder = true,
                startAngle = 270f
            )
            CircularProgressBar(
                modifier = Modifier
                    .width(285.dp)
                    .align(Alignment.Center),
                progress = progressMinutes,
                progressMax = 100f,
                progressBarColor =
                MaterialTheme.colorScheme.secondary,
                progressBarWidth = 20.dp,
                backgroundProgressBarColor = Color.Transparent,
                backgroundProgressBarWidth = 1.dp,
                roundBorder = true,
                startAngle = 290f
            )
            CircularProgressBar(
                modifier = Modifier
                    .width(269.dp)
                    .align(Alignment.Center),
                progress = 39f,
                progressMax = 100f,
                progressBarColor =
                MaterialTheme.colorScheme.secondary,
                progressBarWidth = 4.dp,
                backgroundProgressBarColor = Color.Transparent,
                backgroundProgressBarWidth = 1.dp,
                roundBorder = true,
                startAngle = 290f
            )

            CircularProgressBar(
                modifier = Modifier
                    .width(240.dp)
                    .align(Alignment.Center),
                progress = progressHours,
                progressMax = 100f,
                progressBarColor =
                MaterialTheme.colorScheme.tertiary,
                progressBarWidth = 25.dp,
                backgroundProgressBarColor = Color.Transparent,
                backgroundProgressBarWidth = 1.dp,
                roundBorder = true,
                startAngle = 325f
            )

            CircularProgressBar(
                modifier = Modifier
                    .width(220.dp)
                    .align(Alignment.Center),
                progress = 19.44f,
                progressMax = 100f,
                progressBarColor =
                MaterialTheme.colorScheme.tertiary,
                progressBarWidth = 4.dp,
                backgroundProgressBarColor = Color.Transparent,
                backgroundProgressBarWidth = 1.dp,
                roundBorder = true,
                startAngle = 325f
            )
        }
        TimerTimeInNumbers(
            timerService = timerService,
            modifier = modifier
                .padding(top = 30.dp)
        )
    }
}

private fun calculateTimerFloatAddition(
    progressLength: Float,
    numberOfUnitsInBiggerUnit: Int
): Float {

    return progressLength / (numberOfUnitsInBiggerUnit - 1)
}

@ExperimentalAnimationApi
@Composable
fun TimerTimeInNumbers(
    timerService: TimerService,
    modifier: Modifier = Modifier
) {
    val hours by timerService.hours
    val minutes by timerService.minutes
    val seconds by timerService.seconds

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        timerService.updateTimerTimeLaunchedEffect(context)
    }
    Row {
        AnimatedContent(
            targetState = hours,
            transitionSpec = {
                addAnimation().using(SizeTransform(clip = false))
            }
        ) {
            Text(
                text = hours,
                style = TextStyle(
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold,
                    color =
                        MaterialTheme.colorScheme.tertiary,
                ),
                modifier = Modifier.padding(end = 15.dp)
            )
        }
        AnimatedContent(
            targetState = minutes,
            transitionSpec = {
                addAnimation().using(SizeTransform(clip = false))
            }) {
            Text(
                text = minutes, style = TextStyle(
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold,
                    color =
                        MaterialTheme.colorScheme.secondary,
                ),
                modifier = Modifier.padding(end = 15.dp)
            )
        }
        AnimatedContent(
            targetState = seconds,
            transitionSpec = {
                addAnimation().using(SizeTransform(clip = false))
            }) {
            Text(
                text = seconds, style = TextStyle(
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold,
                    color =
                        MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun TimerStartStopButton(
    timerService: TimerService,
    detoxRankViewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    val currentState by timerService.currentState
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier.fillMaxWidth()) {
        if (currentState == TimerState.Started) {
            OutlinedIconButton(
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context,
                        action = ACTION_SERVICE_CANCEL
                    )
                    coroutineScope.launch {
                        detoxRankViewModel.updateTimerStarted(false)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Stop,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(
                        text = "Finish",
                        style = Typography.bodySmall,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            FilledIconButton(
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context,
                        action = ACTION_SERVICE_START
                    )
                    coroutineScope.launch {
                        detoxRankViewModel.updateTimerStartedTimeMillis()
                        detoxRankViewModel.updateTimerStarted(true)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(
                        text = "Start Detox",
                        style = Typography.bodySmall,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun DifficultySelect(
    onClick: () -> Unit,
    detoxRankUiState: DetoxRankUiState,
    timerService: TimerService,
    modifier: Modifier = Modifier
) {
    val currentState by timerService.currentState
    val iconToDisplay = when (detoxRankUiState.currentTimerDifficulty) {
        TimerDifficulty.Easy -> R.drawable.timer_easy_difficulty_icon
        TimerDifficulty.Medium -> R.drawable.timer_medium_difficulty_icon
        TimerDifficulty.Hard -> R.drawable.timer_hard_difficulty_icon
    }

    val buttonEnabled = currentState != TimerState.Started

    OutlinedIconButton(
        onClick = onClick,
        enabled = buttonEnabled,
        shape = RoundedCornerShape(14.dp),
        border = if (buttonEnabled) { BorderStroke(3.dp, Brush.sweepGradient(
            listOf(
                rank_color,
                rank_color_ultra_dark,
                rank_color,
                rank_color_ultra_dark,
                rank_color)))
        } else { BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceVariant) },
        modifier = modifier
            .width(80.dp)
            .height(60.dp)
            .padding(top = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = iconToDisplay),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .padding(10.dp)
            )
        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 600): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height/20 } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> height/20 } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun TimerBodyPreview() {
//    TimerBody(
//        timerService = TimerService(),
//        detoxRankUiState = DetoxRankUiState(),
//        detoxRankViewModel = DetoxRankViewModel(),
//        timerViewModel = viewModel(),
//        viewModel = viewModel(),
//    )
}
