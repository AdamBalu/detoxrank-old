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
import com.example.detoxrank.ui.theory.screens.chapter_hedonic_circuit.CHHedonicCircuitExample
import com.example.detoxrank.ui.theory.screens.chapter_hedonic_circuit.CHHedonicCircuitIntro
import com.example.detoxrank.ui.theory.screens.chapter_hedonic_circuit.CHHedonicCircuitPoint
import com.example.detoxrank.ui.theory.screens.chapter_hedonic_circuit.CHHedonicCircuitSummary
import com.example.detoxrank.ui.theory.screens.chapter_introduction.CHIntroDilemma
import com.example.detoxrank.ui.theory.screens.chapter_introduction.CHIntroDilemmaCont
import com.example.detoxrank.ui.theory.screens.chapter_introduction.CHIntroIntro
import com.example.detoxrank.ui.theory.screens.chapter_reinforcement.*
import com.example.detoxrank.ui.theory.screens.chapter_solution.*
import com.example.detoxrank.ui.theory.screens.chapter_tolerance.CHToleranceCorrelation
import com.example.detoxrank.ui.theory.screens.chapter_tolerance.CHToleranceExample
import com.example.detoxrank.ui.theory.screens.chapter_tolerance.CHToleranceIntro
import com.example.detoxrank.ui.theory.screens.chapter_tolerance.CHToleranceSummary


/**
 * enum values that represent the screens in the app
 */
enum class TheoryScreen(@StringRes val title: Int) {
    // chapter select
    Chapters(R.string.chapter_select),

    // intro
    CHIntro(R.string.topbar_introduction),
    CHIntroDilemma(R.string.topbar_dilemma),
    CHIntroDilemmaCont(R.string.topbar_dilemma_pt_2),

    // dopamine
    CHDopamine(R.string.topbar_introduction),
    CHDopamineBrain(R.string.topbar_brain),
    CHDopamineNeurotransmitter(R.string.topbar_neurotransmitter),
    CHDopaminePoint(R.string.topbar_point),
    CHDopamineSummary(R.string.topbar_summary),

    // reinforcement
    CHReinforcement(R.string.topbar_introduction),
    CHReinforcementRewardCircuit(R.string.topbar_reward_circuit),
    CHReinforcementExample(R.string.topbar_example),
    CHReinforcementProblems(R.string.topbar_problems),
    CHReinforcementSummary(R.string.topbar_summary),

    // tolerance
    CHTolerance(R.string.topbar_introduction),
    CHToleranceExample(R.string.topbar_example),
    CHToleranceCorrelation(R.string.topbar_correlation),
    CHToleranceSummary(R.string.topbar_summary),

    // hedonic circuit
    CHHedonicCircuit(R.string.topbar_introduction),
    CHHedonicCircuitExample(R.string.topbar_example),
    CHHedonicCircuitPoint(R.string.topbar_point),
    CHHedonicCircuitSummary(R.string.topbar_summary),

    CHSolution(R.string.topbar_introduction),
    CHSolutionAdvice(R.string.topbar_advice),
    CHSolutionAdviceCont(R.string.topbar_advice_pt2),
    CHSolutionAdviceContCont(R.string.topbar_advice_pt3),
    CHSolutionSummary(R.string.topbar_summary)
}

enum class ChapterIndices {
    INTRO_INDEX,
    DETOX_INDEX,
    BRAIN_FUNCTIONS_INDEX,
    REINFORCEMENT_INDEX,
    HEDONIC_CIRCUIT_INDEX,
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

    val onChapterDone = {
        navController.navigate(TheoryScreen.Chapters.name) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
        viewModel.resetProgressBarProgression()
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
                    onCHToleranceSelected = {
                        navController.navigate(TheoryScreen.CHTolerance.name)
                        currentChapter = chapters[ChapterIndices.REINFORCEMENT_INDEX.ordinal]
                    },
                    onCHHedonicCircuitSelected = {
                        navController.navigate(TheoryScreen.CHHedonicCircuit.name)
                        currentChapter = chapters[ChapterIndices.HEDONIC_CIRCUIT_INDEX.ordinal]
                    },
                    onCHSolutionSelected = {
                        navController.navigate(TheoryScreen.CHSolution.name)
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
                    onChapterDone = onChapterDone,
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
                    onChapterDone = onChapterDone,
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
                CHReinforcementSummary(
                    onChapterDone = onChapterDone,
                    backHandler = backHandler
                )
            }

            /**
             * Chapter Tolerance
             */
            composable(route = TheoryScreen.CHTolerance.name) {
                CHToleranceIntro(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHToleranceExample.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHToleranceExample.name) {
                CHToleranceExample(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHToleranceCorrelation.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHToleranceCorrelation.name) {
                CHToleranceCorrelation(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHToleranceSummary.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHToleranceSummary.name) {
                CHToleranceSummary(
                    onChapterDone = onChapterDone,
                    backHandler = backHandler
                )
            }

            /**
             * Chapter Hedonic Circuit
             */
            composable(route = TheoryScreen.CHHedonicCircuit.name) {
                CHHedonicCircuitIntro(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHHedonicCircuitExample.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHHedonicCircuitExample.name) {
                CHHedonicCircuitExample(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHHedonicCircuitPoint.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHHedonicCircuitPoint.name) {
                CHHedonicCircuitPoint(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHHedonicCircuitSummary.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHHedonicCircuitSummary.name) {
                CHHedonicCircuitSummary(
                    onChapterDone = onChapterDone,
                    backHandler = backHandler
                )
            }

            /**
             * Chapter Solution
             */
            composable(route = TheoryScreen.CHSolution.name) {
                CHSolutionIntro(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHSolutionAdvice.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHSolutionAdvice.name) {
                CHSolutionAdvice(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHSolutionAdviceCont.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHSolutionAdviceCont.name) {
                CHSolutionAdviceCont(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHSolutionAdviceContCont.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHSolutionAdviceContCont.name) {
                CHSolutionAdviceContCont(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.CHSolutionSummary.name)
                        viewModel.updateProgressBarProgression(
                            calculateProgressBarAddition(currentChapter)
                        )
                    },
                    backHandler = backHandler
                )
            }
            composable(route = TheoryScreen.CHSolutionSummary.name) {
                CHSolutionSummary(
                    onChapterDone = onChapterDone,
                    backHandler = backHandler
                )
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
