package com.example.detoxrank.ui.theory.screens.chapter_solution

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.md_theme_dark_tertiary
import com.example.detoxrank.ui.theme.md_theme_light_tertiary
import com.example.detoxrank.ui.theory.screens.CompleteChapterIconButton

@Composable
fun CHSolutionSummary(
    modifier: Modifier = Modifier,
    onChapterDone: () -> Unit,
    backHandler: () -> Unit
) {
    val scrollState = rememberScrollState()
    BackHandler(onBack = backHandler)

    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp)
            .verticalScroll(state = scrollState)
    ) {
        CHSolutionSummaryBody()
        CompleteChapterIconButton(
            onClick = onChapterDone,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CHSolutionSummaryBody() {
    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_dopamine_screen_5_pt_1))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("""

    - brain circuits
    - reward circuit
    - drug effects on brain
    - reinforcement learning
           
""")
            }
            append(text = stringResource(id = R.string.chapter_reinforcement_screen_5_pt_1))
        },
        style = Typography.bodyLarge
    )
}
