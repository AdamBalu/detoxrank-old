package com.example.detoxrank.ui.theory

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.detoxrank.R
import com.example.detoxrank.data.Chapter
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.local.LocalChapterDataProvider
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.NavigationItemContent
import com.example.detoxrank.ui.DetoxRankBottomNavigationBar
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.md_theme_dark_tertiary
import com.example.detoxrank.ui.theme.md_theme_light_tertiary
import com.example.detoxrank.ui.theory.screens.TheoryChapterSelectScreen
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
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

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

private fun backHandler(
    navController: NavHostController,
    viewModel: DetoxRankViewModel,
    currentChapter: Chapter
) {
    navController.navigateUp()
    viewModel.updateProgressBarProgression(
        -viewModel.calculateProgressBarAddition(currentChapter)
    )
}

private fun onChaptersDone(
    navController: NavHostController,
    viewModel: DetoxRankViewModel
) {
//    navController.navigate(TheoryScreen.Chapters.name) {
//        popUpTo(navController.graph.startDestinationId) {
//            inclusive = false
//        }
//    }
    navController.popBackStack(TheoryScreen.Chapters.name, inclusive = false)
    viewModel.resetProgressBarProgression()
}

private fun onChapterContinue(
    navController: NavHostController,
    viewModel: DetoxRankViewModel,
    currentChapter: Chapter,
    chapterName: String
) {
    navController.navigate(chapterName)
    viewModel.updateProgressBarProgression(
        viewModel.calculateProgressBarAddition(currentChapter)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryMainScreen(
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier,
    onTabPressed: ((Section) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    currentTab: Section,
    navController: NavHostController = rememberNavController(),
    navigationType: DetoxRankNavigationType
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val bSE = navController.currentBackStackEntryAsState().value
    Log.d("BackStackEntry", "Curr BSE: $bSE")
    val chapters = LocalChapterDataProvider.allChapters

    val currentScreen = TheoryScreen.valueOf(
        backStackEntry?.destination?.route ?: TheoryScreen.Chapters.name
    )

    var currentChapter by remember { mutableStateOf(chapters[0]) }

    Scaffold(
        topBar = {
            TheoryAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                    viewModel.updateProgressBarProgression(
                        -viewModel.calculateProgressBarAddition(currentChapter)
                    )
                },
                viewModel = viewModel
            )
        },
        bottomBar = {
            if (currentScreen == TheoryScreen.Chapters &&
                navigationType == DetoxRankNavigationType.BOTTOM_NAVIGATION) {
                DetoxRankBottomNavigationBar(
                    currentTab = currentTab,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList
                )
            }
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
                        currentChapter = chapters[ChapterIndices.INTRO_INDEX.ordinal]
                        navController.navigate(TheoryScreen.CHIntro.name)
                    },
                    onCHDopamineSelected = {
                        currentChapter = chapters[ChapterIndices.DETOX_INDEX.ordinal]
                        navController.navigate(TheoryScreen.CHDopamine.name)
                    },
                    onCHReinforcementSelected = {
                        currentChapter = chapters[ChapterIndices.BRAIN_FUNCTIONS_INDEX.ordinal]
                        navController.navigate(TheoryScreen.CHReinforcement.name)
                    },
                    onCHToleranceSelected = {
                        currentChapter = chapters[ChapterIndices.REINFORCEMENT_INDEX.ordinal]
                        navController.navigate(TheoryScreen.CHTolerance.name)
                    },
                    onCHHedonicCircuitSelected = {
                        currentChapter = chapters[ChapterIndices.HEDONIC_CIRCUIT_INDEX.ordinal]
                        navController.navigate(TheoryScreen.CHHedonicCircuit.name)
                    },
                    onCHSolutionSelected = {
                        currentChapter = chapters[ChapterIndices.SOLUTIONS_INDEX.ordinal]
                        navController.navigate(TheoryScreen.CHSolution.name)
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
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHIntroDilemma.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHIntroDilemma.name) {
                CHIntroDilemma(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHIntroDilemmaCont.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHIntroDilemmaCont.name) {
                CHIntroDilemmaCont(
                    onChapterDone = { onChaptersDone(navController, viewModel) },
                    backHandler = { backHandler(navController, viewModel, currentChapter) },
                    chapter = currentChapter
                )
            }

            /**
             * Chapter Dopamine
             */
            composable(route = TheoryScreen.CHDopamine.name) {
                CHDopamineIntro(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHDopamineBrain.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHDopamineBrain.name) {
                CHDopamineBrain(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHDopamineNeurotransmitter.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHDopamineNeurotransmitter.name) {
                CHDopamineNeurotransmitter(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHDopaminePoint.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHDopaminePoint.name) {
                CHDopaminePoint(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHDopamineSummary.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }

            composable(route = TheoryScreen.CHDopamineSummary.name) {
                CHDopamineSummary(
                    onChapterDone = { onChaptersDone(navController, viewModel) },
                    backHandler = { backHandler(navController, viewModel, currentChapter) },
                    chapter = currentChapter
                )
            }

            /**
             * Chapter Reinforcement
             */
            composable(route = TheoryScreen.CHReinforcement.name) {
                CHReinforcementIntro(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHReinforcementRewardCircuit.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHReinforcementRewardCircuit.name) {
                CHReinforcementRewardCircuit(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHReinforcementExample.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHReinforcementExample.name) {
                CHReinforcementExample(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHReinforcementProblems.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHReinforcementProblems.name) {
                CHReinforcementProblems(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHReinforcementSummary.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHReinforcementSummary.name) {
                CHReinforcementSummary(
                    onChapterDone = { onChaptersDone(navController, viewModel) },
                    backHandler = { backHandler(navController, viewModel, currentChapter) },
                    chapter = currentChapter
                )
            }

            /**
             * Chapter Tolerance
             */
            composable(route = TheoryScreen.CHTolerance.name) {
                CHToleranceIntro(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHToleranceExample.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHToleranceExample.name) {
                CHToleranceExample(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHToleranceCorrelation.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHToleranceCorrelation.name) {
                CHToleranceCorrelation(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHToleranceSummary.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHToleranceSummary.name) {
                CHToleranceSummary(
                    onChapterDone = { onChaptersDone(navController, viewModel) },
                    backHandler = { backHandler(navController, viewModel, currentChapter) },
                    chapter = currentChapter
                )
            }

            /**
             * Chapter Hedonic Circuit
             */
            composable(route = TheoryScreen.CHHedonicCircuit.name) {
                CHHedonicCircuitIntro(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHHedonicCircuitExample.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHHedonicCircuitExample.name) {
                CHHedonicCircuitExample(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHHedonicCircuitExample.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHHedonicCircuitPoint.name) {
                CHHedonicCircuitPoint(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHHedonicCircuitSummary.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHHedonicCircuitSummary.name) {
                CHHedonicCircuitSummary(
                    onChapterDone = { onChaptersDone(navController, viewModel) },
                    backHandler = { backHandler(navController, viewModel, currentChapter) },
                    chapter = currentChapter
                )
            }

            /**
             * Chapter Solution
             */
            composable(route = TheoryScreen.CHSolution.name) {
                CHSolutionIntro(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHSolutionAdvice.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHSolutionAdvice.name) {
                CHSolutionAdvice(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHSolutionAdviceCont.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHSolutionAdviceCont.name) {
                CHSolutionAdviceCont(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHSolutionAdviceContCont.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHSolutionAdviceContCont.name) {
                CHSolutionAdviceContCont(
                    onChapterContinue = {
                        onChapterContinue(navController, viewModel, currentChapter, TheoryScreen.CHSolutionSummary.name)
                    },
                    backHandler = { backHandler(navController, viewModel, currentChapter) }
                )
            }
            composable(route = TheoryScreen.CHSolutionSummary.name) {
                CHSolutionSummary(
                    onChapterDone = { onChaptersDone(navController, viewModel) },
                    backHandler = { backHandler(navController, viewModel, currentChapter) },
                    chapter = currentChapter
                )
            }
        }
    }
}

/**
 * Image format for theory with optional label as a description
 *
 * for label to work correctly, this component needs to be wrapped in a column
 */
@Composable
fun TheoryImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int? = null,
    @StringRes imageLabel: Int? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
            Image(
                painterResource(id = imageRes),
                contentDescription = stringResource(contentDescription ?: R.string.empty_message),
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 12.dp)
            )

        if (imageLabel != null)
            Text(
                text = "Image: " + stringResource(id = imageLabel),
                style = Typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 25.dp)
            )
        else
            Spacer(Modifier.height(25.dp))
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
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp)  {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
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

@Preview
@Composable
fun TheoryImagePreview() {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        TheoryImage(
            imageRes = R.drawable.reward_circuit,
            imageLabel = R.string.reward_circuit_label
        )
    }
}

@Preview
@Composable
fun TheoryMainScreenPreview() {
    val viewModel: DetoxRankViewModel = viewModel()
    TheoryMainScreen(viewModel = viewModel,
        onTabPressed = { },
        navigationItemContentList = listOf(),
        currentTab = Section.Theory,
        navController = rememberNavController(),
        navigationType = DetoxRankNavigationType.BOTTOM_NAVIGATION
    )
}