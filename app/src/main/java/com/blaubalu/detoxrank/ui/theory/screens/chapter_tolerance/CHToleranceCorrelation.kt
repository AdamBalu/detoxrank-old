package com.blaubalu.detoxrank.ui.theory.screens.chapter_tolerance

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blaubalu.detoxrank.R
import com.blaubalu.detoxrank.ui.theme.Typography
import com.blaubalu.detoxrank.ui.theme.md_theme_dark_tertiary
import com.blaubalu.detoxrank.ui.theme.md_theme_light_tertiary
import com.blaubalu.detoxrank.ui.theory.TheoryImage
import com.blaubalu.detoxrank.ui.theory.screens.ContinueIconButton

@Composable
fun CHToleranceCorrelation(
    onChapterContinue: () -> Unit,
    modifier: Modifier = Modifier,
    backHandler: () -> Unit
) {
    val scrollState = rememberScrollState()
    BackHandler(onBack = backHandler)

    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp, top = 26.dp)
            .verticalScroll(state = scrollState)
    ) {
        CHToleranceCorrelationBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CHToleranceCorrelationBody(
    modifier: Modifier = Modifier
) {
    val darkTheme = isSystemInDarkTheme()
    val boldStyle = SpanStyle(
        color = if (darkTheme) md_theme_dark_tertiary else md_theme_light_tertiary,
        fontWeight = FontWeight.Bold
    )
    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_tolerance_screen_3_pt_1))
            withStyle(style = boldStyle) { append(" tolerance ") }
            append(text = stringResource(id = R.string.chapter_tolerance_screen_3_pt_2))
        },
        style = Typography.bodyLarge
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(
            imageRes = if (darkTheme)
                R.drawable.dopamine_social_media
            else
                R.drawable.dopamine_social_media_light
        )
    }

    Text(
        text = stringResource(R.string.chapter_tolerance_screen_3_pt_3),
        style = Typography.bodyLarge
    )
}

@Preview
@Composable
fun CHToleranceCorrelationPreview() {
    CHToleranceExample(onChapterContinue = { }) {

    }
}
