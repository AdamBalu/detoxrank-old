package com.example.detoxrank.ui.theory.screens.chapter_one

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theory.screens.ContinueIconButton

@Composable
fun ChapterOneScreenThree(
    modifier: Modifier = Modifier,
    onChapterContinue: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp)
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
    ) {
        Text(
            text = stringResource(id = R.string.chapter_1_dopamine_theory_text_4),
            style = Typography.bodyLarge
        )
        Text(
            text = stringResource(id = R.string.chapter_1_dopamine_theory_text_5),
            style = Typography.bodyLarge
        )
        ContinueIconButton(
            onClick = onChapterContinue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}