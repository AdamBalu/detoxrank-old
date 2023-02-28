package com.example.detoxrank.ui.theory

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
import com.example.detoxrank.ui.theory.screens.TheoryChapterSelectScreen

/**
 * enum values that represent the screens in the app
 */
enum class TheoryScreen(@StringRes val title: Int) {
    Chapters(R.string.chapter_select),
    ChapterOne(R.string.chapters_learn),
    ChapterTwo(R.string.chapters_learn),
    ChapterThree(R.string.chapters_learn),
    ChapterFour(R.string.chapters_learn),
    ChapterFive(R.string.chapters_learn)
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
                    onChapterCardClicked = {

                    }
                )
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
        title = { Text(stringResource(currentScreen.title)) },
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

