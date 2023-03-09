package com.example.detoxrank.ui.theory.screens.chapter_dopamine

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun CHDopamineIntro(
    onChapterContinue: () -> Unit,
    modifier: Modifier = Modifier,
    backHandler: () -> Unit
) {
    BackHandler(onBack = backHandler)

    Column(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp, top = 26.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        CHDopamineIntroBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CHDopamineIntroBody(
    modifier: Modifier = Modifier
) {
    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_dopamine_screen_1_pt_1))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" dopamine")
            }
            append(text = stringResource(id = R.string.chapter_dopamine_screen_1_pt_2))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" neurotransmitter ") }
            append(text = stringResource(id = R.string.chapter_dopamine_screen_1_pt_3))
        },
        style = Typography.bodyLarge
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(
            imageRes = R.drawable.dopamine,
            imageLabel = R.string.dopamine_chemical_label,
            contentDescription = R.string.dopamine_chemical_content_description,
            modifier = Modifier.width(290.dp)
        )
    }

    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_dopamine_screen_1_pt_4))
        },
        style = Typography.bodyLarge
    )
}
