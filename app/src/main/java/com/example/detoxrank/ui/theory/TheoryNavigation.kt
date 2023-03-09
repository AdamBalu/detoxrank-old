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
import com.example.detoxrank.ui.theory.screens.chapter_dopamine.*
import com.example.detoxrank.ui.theory.screens.chapter_introduction.CHIntroDilemma
import com.example.detoxrank.ui.theory.screens.chapter_introduction.CHIntroDilemmaCont
import com.example.detoxrank.ui.theory.screens.chapter_introduction.CHIntroIntro
import com.example.detoxrank.ui.theory.screens.chapter_reinforcement.*
import com.example.detoxrank.ui.theory.screens.chapter_tolerance.CHToleranceIntro


/**
 * enum values that represent the screens in the app
 */
enum class TheoryScreen(@StringRes val title: Int) {
    // chapter select
    Chapters(R.string.chapter_select),

    // intro
    CHIntro(R.string.chapters_learn),
    CHIntroDilemma(R.string.chapters_learn),
    CHIntroDilemmaCont(R.string.chapters_learn),

    // dopamine
    CHDopamine(R.string.chapters_learn),
    CHDopamineBrain(R.string.previous_chapter_screen),
    CHDopamineNeurotransmitter(R.string.previous_chapter_screen),
    CHDopaminePoint(R.string.previous_chapter_screen),
    CHDopamineSummary(R.string.previous_chapter_screen),

    // reinforcement
    CHReinforcement(R.string.chapters_learn),
    CHReinforcementRewardCircuit(R.string.chapters_learn),
    CHReinforcementExample(R.string.chapters_learn),
    CHReinforcementProblems(R.string.chapters_learn),
    CHReinforcementSummary(R.string.chapters_learn),

    // tolerance
    CHTolerance(R.string.chapters_learn),

    ChapterThree(R.string.chapters_learn),
    ChapterFour(R.string.chapters_learn),
    ChapterFive(R.string.chapters_learn)
}

enum class ChapterIndices {
    INTRO_INDEX,
    DETOX_INDEX,
    BRAIN_FUNCTIONS_INDEX,
    REINFORCEMENT_INDEX,
    PREP_INDEX,
    SOLUTIONS_INDEX
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
                    onCHIntroSelected = {
                        navController.navigate(TheoryScreen.CHIntro.name)
                        currentChapter = chapters[ChapterIndices.INTRO_INDEX.ordinal]
                    },
                    onCHDopamineSelected = {
                        navController.navigate(TheoryScreen.CHDopamine.name)
                        currentChapter = chapters[ChapterIndices.DETOX_INDEX.ordinal]
                    },
                    onCHReinforcementSelected = {
                        navController.navigate(TheoryScreen.CHReinforcement.name)
                        currentChapter = chapters[ChapterIndices.BRAIN_FUNCTIONS_INDEX.ordinal]
                    },
                    onChapterThreeSelected = {
                        navController.navigate(TheoryScreen.ChapterThree.name)
                        currentChapter = chapters[ChapterIndices.REINFORCEMENT_INDEX.ordinal]
                    },
                    onChapterFourSelected = {
                        navController.navigate(TheoryScreen.ChapterFour.name)
                        currentChapter = chapters[ChapterIndices.PREP_INDEX.ordinal]
                    },
                    onChapterFiveSelected = {
                        navController.navigate(TheoryScreen.ChapterFive.name)
                        currentChapter = chapters[ChapterIndices.SOLUTIONS_INDEX.ordinal]
                    },
                    chapters = chapters
                )
            }

            /**
             * Chapter Introduction
             */
            composable(route = TheoryScreen.CHIntro.name) {
                CHIntroIntro(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHIntroDilemma.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHIntroDilemma.name) {
                CHIntroDilemma(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHIntroDilemmaCont.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHIntroDilemmaCont.name) {
                CHIntroDilemmaCont(
                    onChapterDone = {
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
             * Chapter Dopamine
             */
            composable(route = TheoryScreen.CHDopamine.name) {
                CHDopamineIntro(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHDopamineBrain.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHDopamineBrain.name) {
                CHDopamineBrain(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHDopamineNeurotransmitter.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHDopamineNeurotransmitter.name) {
                CHDopamineNeurotransmitter(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHDopaminePoint.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHDopaminePoint.name) {
                CHDopaminePoint(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHDopamineSummary.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }

            composable(route = TheoryScreen.CHDopamineSummary.name) {
                CHDopamineSummary(
                    onChapterDone = {
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
             * Chapter Reinforcement
             */
            composable(route = TheoryScreen.CHReinforcement.name) {
                CHReinforcementIntro(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHReinforcementRewardCircuit.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHReinforcementRewardCircuit.name) {
                CHReinforcementRewardCircuit(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHReinforcementExample.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHReinforcementExample.name) {
                CHReinforcementExample(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHReinforcementProblems.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHReinforcementProblems.name) {
                CHReinforcementProblems(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHReinforcementSummary.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHReinforcementSummary.name) {
                CHReinforcementSummary(onChapterDone = {
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
             * Chapter Tolerance
             */
            composable(route = TheoryScreen.CHTolerance.name) {
                CHToleranceIntro(
                    onChapterContinue = {
                        /*TODO*/
                    },
                    backHandler = backHandler
                )
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
