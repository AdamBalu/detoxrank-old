package com.example.detoxrank.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.detoxrank.DetoxRankApp
import com.example.detoxrank.ui.tasks.home.TasksHomeViewModel
import com.example.detoxrank.ui.tasks.task.TaskViewModel
import com.example.detoxrank.ui.theory.TheoryViewModel
import com.example.detoxrank.ui.timer.TimerViewModel

object DetoxRankViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DetoxRankViewModel()
        }

        initializer {
            TasksHomeViewModel(
                tasksRepository = detoxRankApplication().container.tasksRepository
            )
        }

        initializer {
            TaskViewModel(
                this.createSavedStateHandle(),
                tasksRepository = detoxRankApplication().container.tasksRepository
            )
        }

        initializer {
            TheoryViewModel(
                this.createSavedStateHandle(),
                chaptersRepository = detoxRankApplication().container.chaptersRepository
            )
        }

        initializer {
            TimerViewModel()
        }

        initializer {
            UserDataViewModel(
                userDataRepository = detoxRankApplication().container.userDataRepository
            )
        }
    }
}

fun CreationExtras.detoxRankApplication(): DetoxRankApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DetoxRankApp)
