package com.example.detoxrank.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.detoxrank.DetoxRankApp
import com.example.detoxrank.ui.rank.RankViewModel
import com.example.detoxrank.ui.tasks.home.TasksHomeViewModel
import com.example.detoxrank.ui.tasks.task.TaskViewModel
import com.example.detoxrank.ui.theory.TheoryViewModel
import com.example.detoxrank.ui.timer.TimerViewModel

object DetoxRankViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DetoxRankViewModel(
                userDataRepository = detoxRankApplication().container.userDataRepository,
                achievementRepository = detoxRankApplication().container.achievementRepository
            )
        }

        initializer {
            RankViewModel(
                achievementRepository = detoxRankApplication().container.achievementRepository,
                userDataRepository = detoxRankApplication().container.userDataRepository
            )
        }

        initializer {
            TasksHomeViewModel(
                tasksRepository = detoxRankApplication().container.tasksRepository
            )
        }

        initializer {
            TaskViewModel(
                detoxRankApplication(),
                tasksRepository = detoxRankApplication().container.tasksRepository,
                wmTasksRepository = detoxRankApplication().container.wmTasksRepository
//                application = this[APPLICATION_KEY] as DetoxRankApp
            )
        }

        initializer {
            TheoryViewModel(
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
