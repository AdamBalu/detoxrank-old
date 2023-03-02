package com.example.detoxrank.ui.theory

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.detoxrank.R
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theory.screens.*
import com.example.detoxrank.ui.theory.screens.chapter_one.ChapterOneScreenThree
import com.example.detoxrank.ui.theory.screens.chapter_one.ChapterOneScreenTwo
import com.example.detoxrank.ui.theory.screens.chapter_one.ChapterOneStartScreen

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
    ChapterOnePtThree(R.string.previous_chapter_screen)
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

    val currentScreen = TheoryScreen.valueOf(
        backStackEntry?.destination?.route ?: TheoryScreen.Chapters.name
    )

    BackHandler {
        viewModel.updateTheoryLaunchState(false)
    }

    Scaffold(
        topBar = {
            DetoxRankAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
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
                TheoryChapterSelectScreen(
                    onChapterOneSelected = { navController.navigate(TheoryScreen.ChapterOne.name) },
                    onChapterTwoSelected = { navController.navigate(TheoryScreen.ChapterTwo.name) },
                    onChapterThreeSelected = { navController.navigate(TheoryScreen.ChapterThree.name) },
                    onChapterFourSelected = { navController.navigate(TheoryScreen.ChapterFour.name) },
                    onChapterFiveSelected = { navController.navigate(TheoryScreen.ChapterFive.name) }
                )
            }

            /**
             * Chapter one
             */
            composable(route = TheoryScreen.ChapterOne.name) {
                ChapterOneStartScreen(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.ChapterOnePtTwo.name)
                    }
                )
            }
            composable(route = TheoryScreen.ChapterOnePtTwo.name) {
                ChapterOneScreenTwo(
                    onChapterContinue = {
                        navController.navigate(TheoryScreen.ChapterOnePtThree.name)
                    }
                )
            }
            composable(route = TheoryScreen.ChapterOnePtThree.name) {
                ChapterOneScreenThree(
                    onChapterContinue = {
                    }
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
fun DetoxRankAppBar(
    currentScreen: TheoryScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {

    val navigateUpFromChapters = { viewModel.updateTheoryLaunchState(false) }

    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = Typography.titleMedium
            )},
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = if (canNavigateBack) navigateUp else navigateUpFromChapters) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
}
