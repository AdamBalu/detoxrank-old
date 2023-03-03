package com.example.detoxrank.ui.theory.screens.chapter_one

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
import com.example.detoxrank.ui.theory.TheoryImage
import com.example.detoxrank.ui.theory.screens.ContinueIconButton

@Composable
fun ChapterOneScreenFour(
    modifier: Modifier = Modifier,
    onChapterContinue: () -> Unit,
    backHandler: () -> Unit
) {
    val scrollState = rememberScrollState()
    BackHandler(onBack = backHandler)

    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp)
            .verticalScroll(state = scrollState)
    ) {
        ChapterOneScreenFourBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun ChapterOneScreenFourBody() {
    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4_part_1))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" reinforces your behaviors ")
            }
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4_part_2))
        },
        style = Typography.bodyLarge
    )
    TheoryImage(
        imageRes = R.drawable.want_like_difference
    )
    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4_part_3))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" wanting ")
            }
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4_part_4))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" liking ")
            }
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4_part_5))
        },
        style = Typography.bodyLarge
    )
    TheoryImage(imageRes = R.drawable.time_passing)
    Text(
        text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4_part_6),
        style = Typography.bodyLarge
    )
}