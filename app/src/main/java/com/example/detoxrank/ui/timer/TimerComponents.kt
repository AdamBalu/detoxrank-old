package com.example.detoxrank.ui.timer

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.detoxrank.R
import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.service.ServiceHelper
import com.example.detoxrank.service.TimerService
import com.example.detoxrank.service.TimerState
import com.example.detoxrank.ui.DetoxRankUiState
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.rank.AchievementViewModel
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.rank_color
import com.example.detoxrank.ui.theme.rank_color_ultra_dark
import com.example.detoxrank.ui.utils.Constants
import com.example.detoxrank.ui.utils.Constants.ID_START_TIMER
import com.example.detoxrank.ui.utils.calculateTimerFloatAddition
import com.hitanshudhawan.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

@ExperimentalAnimationApi
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
            timerService = timerService
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun TimerTimeInNumbers(
    timerService: TimerService
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
    achievementViewModel: AchievementViewModel,
    modifier: Modifier = Modifier
) {
    val currentState by timerService.currentState
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var wasButtonClicked by remember { mutableStateOf(false) }
//    var wasDismissAlertClicked by remember { mutableStateOf(false) }
//    var showEndDetoxDialog by remember { mutableStateOf(false) }

//    if (showEndDetoxDialog) {
//        SaveTimerProgressDialog(
//            onConfirm = {
//                ServiceHelper.triggerForegroundService(
//                    context = context,
//                    action = Constants.ACTION_SERVICE_CANCEL
//                )
//                coroutineScope.launch {
//                    detoxRankViewModel.updateTimerStarted(false)
//                }
//                showEndDetoxDialog = false
//            },
//            onDismiss = {
//                if (!wasDismissAlertClicked) {
//                    wasDismissAlertClicked = true
//                    Toast.makeText(context, "Tap again if you really wish to delete", Toast.LENGTH_SHORT).show()
//                    coroutineScope.launch {
//                        delay(3000)
//                        wasDismissAlertClicked = false
//                    }
//                } else {
//                    wasDismissAlertClicked = false
//                    ServiceHelper.triggerForegroundService(
//                        context = context,
//                        action = Constants.ACTION_SERVICE_CANCEL
//                    )
//                    coroutineScope.launch {
//                        detoxRankViewModel.updateTimerStarted(false)
//                    }
//                    showEndDetoxDialog = false
//                }
//            }
//        )
//    }

    Box(modifier = modifier.fillMaxWidth()) {
        if (currentState == TimerState.Started) {
            OutlinedIconButton(
                onClick = {
                    if (!wasButtonClicked) {
                        Toast.makeText(context, "Tap again to end the timer", Toast.LENGTH_SHORT).show()
                        wasButtonClicked = true
                        coroutineScope.launch {
                            delay(2000)
                            wasButtonClicked = false
                        }
                    } else {
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = Constants.ACTION_SERVICE_CANCEL
                        )
                        coroutineScope.launch {
                            achievementViewModel.achieveTimerAchievements(timerService.days.value)
                            detoxRankViewModel.updateTimerStarted(false)
                        }
                        wasButtonClicked = false
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
                        action = Constants.ACTION_SERVICE_START
                    )
                    coroutineScope.launch {
                        achievementViewModel.achieveAchievement(ID_START_TIMER)
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
fun TimerFooter(
    timerService: TimerService,
    detoxRankUiState: DetoxRankUiState,
    detoxRankViewModel: DetoxRankViewModel,
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
                detoxRankUiState = detoxRankUiState,
                detoxRankViewModel = detoxRankViewModel
            )
        }
    }
}

//@Composable
//fun SaveTimerProgressDialog(
//    onConfirm: () -> Unit,
//    onDismiss: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text(stringResource(R.string.save_timer_progress_dialog_title)) },
//        text = { Text(stringResource(R.string.save_timer_progress_dialog_text)) },
//        confirmButton = {
//            Button(
//                onClick = onConfirm
//            ) {
//                Text(stringResource(R.string.save_timer_progress_dialog_confirm))
//            }
//        },
//        dismissButton = {
//            Button(
//                onClick = onDismiss
//            ) {
//                Text(stringResource(R.string.save_timer_progress_dialog_dismiss))
//            }
//        }
//    )
//}


@ExperimentalAnimationApi
@Composable
fun DifficultySelect(
    onClick: () -> Unit,
    detoxRankUiState: DetoxRankUiState,
    detoxRankViewModel: DetoxRankViewModel,
    timerService: TimerService,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        val timerDifficulty = detoxRankViewModel.getUserTimerDifficulty()
        detoxRankViewModel.setCurrentTimerDifficulty(timerDifficulty)
    }
    val currentState by timerService.currentState
    val iconToDisplay = when (detoxRankUiState.currentTimerDifficulty) {
        TimerDifficulty.Easy -> R.drawable.timer_easy_difficulty_icon
        TimerDifficulty.Medium -> R.drawable.timer_medium_difficulty_icon
        TimerDifficulty.Hard -> R.drawable.timer_hard_difficulty_icon
    }

    OutlinedIconButton(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        border = if (currentState != TimerState.Started) { BorderStroke(3.dp, Brush.sweepGradient(
            listOf(
                rank_color,
                rank_color_ultra_dark,
                rank_color,
                rank_color_ultra_dark,
                rank_color
            )))
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
