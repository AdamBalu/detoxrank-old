package com.example.detoxrank.ui.tasks

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.ui.DetoxRankViewModel
import com.example.detoxrank.ui.data.Task
import com.example.detoxrank.ui.data.local.LocalTasksDataProvider
import com.example.detoxrank.ui.theme.Typography

@Composable
fun TasksMainScreen(
    modifier: Modifier = Modifier,
    viewModel: DetoxRankViewModel
) {
    val tasks = LocalTasksDataProvider.tasks
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(tasks) {task ->
            TaskItem(
                task = task
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 16.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(task.description),
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
                    text = stringResource(task.longDescription ?: R.string.empty_message),
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                FilledTonalButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "hey jude debug this")
                }
            }
        }
    }
}
