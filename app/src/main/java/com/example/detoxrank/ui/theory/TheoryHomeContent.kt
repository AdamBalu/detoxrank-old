package com.example.detoxrank.ui.theory

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.theme.Typography
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

/**
 * Image in theory with optional label as description
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
        Text("hey??")
    }

}