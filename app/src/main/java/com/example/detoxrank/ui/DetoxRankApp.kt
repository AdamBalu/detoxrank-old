package com.example.detoxrank.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detoxrank.data.Section
import com.example.detoxrank.ui.utils.DetoxRankNavigationType

/**
 * Main composable which displays content
 */
@Composable
fun DetoxRankApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: DetoxRankViewModel = viewModel()
    val detoxRankUiState = viewModel.uiState.collectAsState().value

    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            DetoxRankNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            DetoxRankNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            DetoxRankNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            DetoxRankNavigationType.BOTTOM_NAVIGATION
        }
    }

    DetoxRankHomeScreen(
        viewModel = viewModel,
        detoxRankUiState = detoxRankUiState,
        navigationType = navigationType,
        onTabPressed = { section: Section ->
            viewModel.updateCurrentSection(section = section)
            // TODO reset home screen states if needed
        },
        modifier = modifier
    )

}