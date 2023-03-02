package com.example.detoxrank.ui.theory.screens.chapter_one

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.example.detoxrank.ui.theory.TheoryImage
import com.example.detoxrank.ui.theory.screens.ContinueIconButton

@Composable
fun ChapterOneStartScreen(
    onChapterContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 26.dp, end = 26.dp)
    ) {
        ChapterOneStartScreenBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun ChapterOneStartScreenBody(
    modifier: Modifier = Modifier
) {
    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_1))
            withStyle(
                style = SpanStyle(
                    color = md_theme_dark_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" dopamine")
            }
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_1_part_2))
            withStyle(
                style = SpanStyle(
                    color = md_theme_dark_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" neurotransmitter ") }
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_1_part_3))
        },
        style = Typography.bodyLarge
    )

    TheoryImage(
        imageRes = R.drawable.dopamine,
        imageLabel = R.string.dopamine_chemical_label,
        contentDescription = R.string.dopamine_chemical_content_description,
        includeImageTag = true
    )

    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_1_dopamine_theory_text_1_part_4))
        },
        style = Typography.bodyLarge
    )
}
