package com.example.detoxrank.ui.theory.screens.chapter_reinforcement

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
import com.example.detoxrank.R
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.md_theme_dark_tertiary
import com.example.detoxrank.ui.theme.md_theme_light_tertiary
import com.example.detoxrank.ui.theory.TheoryImage
import com.example.detoxrank.ui.theory.screens.ContinueIconButton

@Composable
fun CHReinforcementExample(
    onChapterContinue: () -> Unit,
    modifier: Modifier = Modifier,
    backHandler: () -> Unit
) {
    val scrollState = rememberScrollState()
    BackHandler(onBack = backHandler)

    Column(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp, top = 26.dp)
            .verticalScroll(state = scrollState)
    ) {
        CHReinforcementExampleBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CHReinforcementExampleBody(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.chapter_reinforcement_screen_3_pt_1),
        style = Typography.bodyLarge
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(imageRes = R.drawable.drugs)
    }

    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_reinforcement_screen_3_pt_2))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" direct stimulants")
            }
            append(".")
        },
        style = Typography.bodyLarge
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(
            imageRes = R.drawable.reward_circuit_stimulated,
            imageLabel = R.string.reward_circuit_stimulated_label,
            contentDescription = R.string.reward_circuit_stimulated_label
        )
    }

    Text(text = stringResource(id = R.string.chapter_reinforcement_screen_3_pt_3), style = Typography.bodyLarge)
}

@Preview
@Composable
fun CHReinforcementExamplePreview() {
    CHReinforcementExample(onChapterContinue = { /*TODO*/ }) {

    }
}
