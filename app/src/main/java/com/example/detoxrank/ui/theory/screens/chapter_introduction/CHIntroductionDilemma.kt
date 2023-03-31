package com.example.detoxrank.ui.theory.screens.chapter_introduction

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
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theory.TheoryImage
import com.example.detoxrank.ui.theory.screens.ContinueIconButton

@Composable
fun CHIntroDilemma(
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
        CHIIntroDilemmaBody()
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CHIIntroDilemmaBody(
    modifier: Modifier = Modifier
) {
    Text(
        stringResource(id = R.string.chapter_intro_screen_2_pt_1),
        style = Typography.bodyLarge
    )
    TheoryImage(
        imageRes = if (isSystemInDarkTheme())
            R.drawable.tasks_with_timer
        else
            R.drawable.tasks_with_timer_light
    )
    Text(
        stringResource(id = R.string.chapter_intro_screen_2_pt_2),
        style = Typography.bodyLarge
    )
}

