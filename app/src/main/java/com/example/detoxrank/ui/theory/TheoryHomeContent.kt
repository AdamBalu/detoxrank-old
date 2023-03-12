package com.example.detoxrank.ui.theory

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.NavigationItemContent
import com.example.detoxrank.ui.data.Section
import com.example.detoxrank.ui.theme.Typography



@Composable
fun TheoryMainScreen(
    viewModel: DetoxRankViewModel,
    modifier: Modifier = Modifier,
    onTabPressed: ((Section) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    currentTab: Section,
) {
    val uiState by viewModel.uiState.collectAsState()

//    if (uiState.isTheoryLaunched) {
    TheoryNavigation(
        viewModel = viewModel,
        onTabPressed = onTabPressed,
        navigationItemContentList = navigationItemContentList,
        currentTab = currentTab
    )
//    } else {
//
//        Column (
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween,
//            modifier = modifier
//                .fillMaxHeight(0.8f)
//                ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    "Theory",
//                    style = Typography.headlineLarge,
//                    textAlign = TextAlign.Center,
//                    modifier = modifier.padding(bottom = 30.dp)
//                )
//                Image(
//                    painterResource(id = R.drawable.brain_enlighted),
//                    contentDescription = null,
//                    modifier = modifier.width(300.dp)
//                )
//            }
//
//            FilledTonalButton(
//                shape = RoundedCornerShape(20),
//                onClick = { viewModel.updateTheoryLaunchState(true) },
//                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp),
//                elevation = ButtonDefaults.buttonElevation(
//                    defaultElevation = 10.dp,
//                    pressedElevation = 3.dp,
//                    disabledElevation = 0.dp,
//                    hoveredElevation = 5.dp,
//                    focusedElevation = 5.dp
//                )
//            ) {
//                Text(text = stringResource(R.string.button_select_chapter))
//                Icon(
//                    imageVector = Icons.Outlined.Start,
//                    contentDescription = null,
//                    modifier = Modifier.padding(start = 10.dp)
//                )
//            }
//        }

}

/**
 * Image format for theory with optional label as a description
 *
 * for label to work correctly, this component needs to be wrapped in a column
 */
@Composable
fun TheoryImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int? = null,
    @StringRes imageLabel: Int? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
            Image(
                painterResource(id = imageRes),
                contentDescription = stringResource(contentDescription ?: R.string.empty_message),
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 12.dp)
            )

        if (imageLabel != null)
            Text(
                text = "Image: " + stringResource(id = imageLabel),
                style = Typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 25.dp)
            )
        else
            Spacer(Modifier.height(25.dp))
    }
}

@Preview
@Composable
fun TheoryImagePreview() {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        TheoryImage(
            imageRes = R.drawable.reward_circuit,
            imageLabel = R.string.reward_circuit_label
        )
    }
}

@Preview
@Composable
fun TheoryMainScreenPreview() {
//    val viewModel: DetoxRankViewModel = viewModel()
//    TheoryMainScreen(viewModel = viewModel)
}