package com.example.detoxrank.ui.theory

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.detoxrank.R
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.data.Chapter
import com.example.detoxrank.ui.data.local.LocalChapterDataProvider
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.md_theme_dark_tertiary
import com.example.detoxrank.ui.theme.md_theme_light_tertiary
import com.example.detoxrank.ui.theory.screens.*
import com.example.detoxrank.ui.theory.screens.chapter_one.*

/**
 * enum values that represent the screens in the app
 */
enum class TheoryScreen(@StringRes val title: Int) {
    Chapters(R.string.chapter_select),
    ChapterOne(R.string.chapters_learn),
    ChapterTwo(R.string.chapters_learn),
    ChapterThree(R.string.chapters_learn),
    ChapterFour(R.string.chapters_learn),
    ChapterFive(R.string.chapters_learn),
    ChapterOnePtTwo(R.string.previous_chapter_screen),
    ChapterOnePtThree(R.string.previous_chapter_screen),
    ChapterOnePtFour(R.string.previous_chapter_screen),
    ChapterOnePtFinal(R.string.previous_chapter_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryNavigation(
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    val chapters = LocalChapterDataProvider.allChapters

    val currentScreen = TheoryScreen.valueOf(
        backStackEntry?.destination?.route ?: TheoryScreen.Chapters.name
    )

    var currentChapter by remember { mutableStateOf(chapters[0]) }

    BackHandler {
        viewModel.updateTheoryLaunchState(false)
    }

    val backHandler = {
        navController.navigateUp()
        viewModel.updateProgressBarProgression(
            -calculateProgressBarAddition(currentChapter)
        )
    }

    Scaffold(
        topBar = {
            TheoryAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                    viewModel.updateProgressBarProgression(
                        -calculateProgressBarAddition(currentChapter)
                    )
                },
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TheoryScreen.Chapters.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = TheoryScreen.Chapters.name) {
                viewModel.resetProgressBarProgression()

                TheoryChapterSelectScreen(
                    onChapterOneSelected = {
                        navController.navigate(TheoryScreen.ChapterOne.name)
                        currentChapter = chapters[0]
                    },
                    onChapterTwoSelected = {
                        navController.navigate(TheoryScreen.ChapterTwo.name)
                        currentChapter = chapters[1]
                    },
                    onChapterThreeSelected = {
                        navController.navigate(TheoryScreen.ChapterThree.name)
                        currentChapter = chapters[2]
                    },
                    onChapterFourSelected = {
                        navController.navigate(TheoryScreen.ChapterFour.name)
                        currentChapter = chapters[3]
                    },
                    onChapterFiveSelected = {
                        navController.navigate(TheoryScreen.ChapterFive.name)
                        currentChapter = chapters[4]
                    },
                    chapters = chapters
                )
            }

            /**
             * Chapter one
             */
            composable(route = TheoryScreen.ChapterOne.name) {
                ChapterOneStartScreen(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.ChapterOnePtTwo.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.ChapterOnePtTwo.name) {
                ChapterOneScreenTwo(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.ChapterOnePtThree.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.ChapterOnePtThree.name) {
                ChapterOneScreenThree(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.ChapterOnePtFour.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.ChapterOnePtFour.name) {
                ChapterOneScreenFour(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.ChapterOnePtFinal.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }

            composable(route = TheoryScreen.ChapterOnePtFinal.name) {
                ChapterOneScreenFinal(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.Chapters.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                        viewModel.resetProgressBarProgression()
                    },
                    backHandler = backHandler
                )
            }

            /**
             * Chapter two
             */
            composable(route = TheoryScreen.ChapterTwo.name) {
                ChapterTwoStartScreen()
            }
            composable(route = TheoryScreen.ChapterThree.name) {
                ChapterThreeStartScreen()
            }
            composable(route = TheoryScreen.ChapterFour.name) {
                ChapterFourStartScreen()
            }
            composable(route = TheoryScreen.ChapterFive.name) {
                ChapterFiveStartScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryAppBar(
    currentScreen: TheoryScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {

    val navigateUpFromChapters = { viewModel.updateTheoryLaunchState(false) }

    val animatedProgress = animateFloatAsState(
        targetValue = viewModel.getProgressBarValue(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    Column(
        modifier = modifier.padding(bottom = 7.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(currentScreen.title),
                    style = Typography.titleMedium
                )},
            navigationIcon = {
                IconButton(onClick = if (canNavigateBack) navigateUp else navigateUpFromChapters) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        )
        if (currentScreen != TheoryScreen.Chapters)
            LinearProgressIndicator(
                progress = animatedProgress,
                color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                modifier = modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(50.dp))

            )
    }
}

private fun calculateProgressBarAddition(chapter: Chapter): Float =
    if (chapter.chapterScreenNum != 0)
        (1 / (chapter.chapterScreenNum - 1).toFloat())
    else 0f
