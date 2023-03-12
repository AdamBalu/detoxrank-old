package com.example.detoxrank.ui.theory.screens.chapter_tolerance

import androidx.activity.compose.BackHandler
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
import com.example.detoxrank.ui.data.Chapter
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theory.screens.CompleteChapterIconButton

@Composable
fun CHToleranceSummary(
    modifier: Modifier = Modifier,
    onChapterDone: () -> Unit,
    backHandler: () -> Unit,
    chapter: Chapter
) {
    val scrollState = rememberScrollState()
    BackHandler(onBack = backHandler)

    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp)
            .verticalScroll(state = scrollState)
    ) {
        CHToleranceSummaryBody()
        CompleteChapterIconButton(
            onClick = onChapterDone,
            modifier = Modifier.align(Alignment.End),
            chapter = chapter
        )
    }
}

@Composable
fun CHToleranceSummaryBody() {
    Text(
        text = stringResource(id = R.string.chapter_tolerance_screen_4_pt_1),
        style = Typography.bodyLarge
    )
}
