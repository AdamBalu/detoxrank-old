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
fun CHDopamineBrain(
    modifier: Modifier = Modifier,
    onChapterContinue: () -> Unit,
    backHandler: () -> Unit
) {
    val scrollState = rememberScrollState()
    BackHandler(onBack = backHandler)

    val mod = modifier
        .padding(start = 26.dp, end = 26.dp)
        .verticalScroll(state = scrollState)

    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp)
            .verticalScroll(state = scrollState)
    ) {
        CHDopamineBrainBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CHDopamineBrainBody(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.chapter_dopamine_screen_2_pt_1),
        style = Typography.bodyLarge
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(
            imageRes = R.drawable.brain,
            modifier = modifier.width(200.dp)
        )
    }

    Text(
        text = stringResource(id = R.string.chapter_dopamine_screen_2_pt_2),
        style = Typography.bodyLarge
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(
            imageRes = R.drawable.brain_neurons,
            contentDescription = R.string.brain_neurons_content_description,
            imageLabel = R.string.brain_neurons_label,
            modifier = modifier.width(280.dp)
        )
    }

    Text(
        buildAnnotatedString {
            append(text = stringResource(id = R.string.chapter_dopamine_screen_2_pt_3))
            withStyle(
                style = SpanStyle(
                    color = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" neuron") }
            append(".")
        },
        style = Typography.bodyLarge
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TheoryImage(
            imageRes = R.drawable.neuron,
            contentDescription = R.string.neuron,
            imageLabel = R.string.neuron,
            modifier = modifier.width(250.dp)
        )
    }
}