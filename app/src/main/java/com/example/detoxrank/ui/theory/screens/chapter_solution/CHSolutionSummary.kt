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
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theory.TheoryImage
import com.example.detoxrank.ui.theory.TheoryViewModel
import com.example.detoxrank.ui.theory.screens.CompleteChapterIconButton

@Composable
fun CHSolutionSummary(
    modifier: Modifier = Modifier,
    onChapterDone: () -> Unit,
    backHandler: () -> Unit,
    chapterName: String,
    theoryViewModel: TheoryViewModel
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
            modifier = Modifier.align(Alignment.End),
            chapterName = chapterName,
            theoryViewModel = theoryViewModel
        )
    }
}

@Composable
fun CHSolutionSummaryBody() {
    Text(
        text = stringResource(id = R.string.chapter_solution_screen_5_pt_1),
        style = Typography.bodyLarge
    )
    TheoryImage(imageRes = if (isSystemInDarkTheme()) R.drawable.timer else R.drawable.timer_light)
    Text(
        text = stringResource(id = R.string.chapter_solution_screen_5_pt_2),
        style = Typography.bodyLarge
    )
}
