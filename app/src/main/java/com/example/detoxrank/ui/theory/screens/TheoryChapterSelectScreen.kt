package com.example.detoxrank.ui.theory.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.detoxrank.R
import com.example.detoxrank.ui.data.Chapter
import com.example.detoxrank.ui.data.ChapterDifficulty
import com.example.detoxrank.ui.data.ChapterTag
import com.example.detoxrank.ui.theme.Typography
import com.example.detoxrank.ui.theme.md_theme_dark_tertiary
import com.example.detoxrank.ui.theme.md_theme_light_tertiary
import com.example.detoxrank.ui.theme.rank_color

@Composable
fun TheoryChapterSelectScreen(
    onCHIntroSelected: () -> Unit,
    onCHDopamineSelected: () -> Unit,
    onCHReinforcementSelected: () -> Unit,
    onChapterThreeSelected: () -> Unit,
    onChapterFourSelected: () -> Unit,
    onChapterFiveSelected: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    chapters: MutableList<Chapter>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        items(chapters) {chapter ->
            val chapterButtonBehavior: () -> Unit = when (chapter.tag) {
                ChapterTag.Introduction -> onCHIntroSelected
                ChapterTag.Dopamine -> onCHDopamineSelected
                ChapterTag.Reinforcement -> onCHReinforcementSelected
                ChapterTag.Tolerance -> onChapterThreeSelected
                ChapterTag.PREP -> onChapterFourSelected
                ChapterTag.Solutions -> onChapterFiveSelected
            }
            TheoryChapter(
                onChapterSelected = chapterButtonBehavior,
                chapter = chapter,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryChapter(
    onChapterSelected: () -> Unit,
    chapter: Chapter,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(vertical = 4.dp, horizontal = 16.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .animateContentSize(animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = chapter.name,
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Icon(
                    imageVector = if (expanded)
                        Icons.Filled.ExpandLess
                    else
                        Icons.Filled.ExpandMore,
                    contentDescription = null
                )
            }
            if (expanded) {
                Text(
                    text = chapter.description,
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                FilledTonalButton(
                    onClick = onChapterSelected,
                    modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.select))
                }
            }
            TheoryChapterFooter(chapter)

        }
    }
}

@Composable
fun TheoryChapterFooter(
    chapter: Chapter,
    modifier: Modifier = Modifier
) {
    val rankPointsGain = when (chapter.difficulty) {
        ChapterDifficulty.Easy -> 500
        ChapterDifficulty.Medium -> 1000
        ChapterDifficulty.Hard -> 1500
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        // xp gain footer
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) md_theme_dark_tertiary else md_theme_light_tertiary,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "$rankPointsGain RP",
                style = Typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        // stars (difficulty level) footer
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = rank_color,
                modifier = Modifier.size(20.dp)
            )
            Icon(
                imageVector = if (
                    chapter.difficulty == ChapterDifficulty.Medium ||
                    chapter.difficulty == ChapterDifficulty.Hard)
                    Icons.Filled.Star
                else
                    Icons.Outlined.Grade,
                contentDescription = null,
                tint = rank_color,
                modifier = Modifier.size(20.dp)
            )
            Icon(
                imageVector = if (chapter.difficulty == ChapterDifficulty.Hard)
                    Icons.Filled.Star
                else
                    Icons.Outlined.Grade,
                contentDescription = null,
                tint = rank_color,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ContinueIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier.width(160.dp).padding(16.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.button_continue),
                style = Typography.bodyMedium,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 5.dp).align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Filled.ArrowRightAlt,
                contentDescription = null
            )
        }

    }
}

@Composable
fun CompleteChapterIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier.width(160.dp).padding(16.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.button_complete_chapter),
                style = Typography.bodyMedium,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 5.dp).align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null
            )
        }

    }
}