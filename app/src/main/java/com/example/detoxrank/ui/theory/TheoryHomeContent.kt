package com.example.detoxrank.ui.theory

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowRight
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.theme.md_theme_dark_primaryContainer
import com.example.detoxrank.ui.theme.md_theme_light_primaryContainer



@Composable
fun TheoryMainScreen(
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isTheoryLaunched) {
        TheoryNavigation(viewModel = viewModel)
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.School,
                contentDescription = null,
                modifier = modifier.size(350.dp),
                tint = if (isSystemInDarkTheme())
                    md_theme_dark_primaryContainer
                else
                    md_theme_light_primaryContainer
            )
            FilledTonalButton(
                onClick = { viewModel.updateTheoryLaunchState(true) },
                contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp, start = 30.dp, end = 30.dp)
            ) {
                Text(text = stringResource(R.string.button_select_chapter))
                Icon(
                    imageVector = Icons.Outlined.KeyboardDoubleArrowRight,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }


}



