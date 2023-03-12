package com.example.detoxrank.ui.data.local

import com.example.detoxrank.R
import com.example.detoxrank.ui.data.Chapter
import com.example.detoxrank.ui.data.ChapterDifficulty
import com.example.detoxrank.ui.data.ChapterTag

object LocalChapterDataProvider {
    val allChapters = listOf(
        Chapter(
            name = R.string.chapter_name_introduction,
            description = R.string.chapter_description_introduction,
            difficulty = ChapterDifficulty.Easy,
            tag = ChapterTag.Introduction,
            chapterScreenNum = 3,
            startChapterButtonLabel = R.string.chapter_introduction_start_chapter_button_label
        ),
        Chapter(
            name = R.string.chapter_name_dopamine,
            description = R.string.chapter_description_dopamine,
            difficulty = ChapterDifficulty.Medium,
            tag = ChapterTag.Dopamine,
            chapterScreenNum = 5,
            startChapterButtonLabel = R.string.chapter_dopamine_start_chapter_button_label
        ),
        Chapter(
            name = R.string.chapter_name_reinforcement,
            description = R.string.chapter_description_reinforcement,
            difficulty = ChapterDifficulty.Hard,
            tag = ChapterTag.Reinforcement,
            chapterScreenNum = 5,
            startChapterButtonLabel = R.string.chapter_reinforcement_start_chapter_button_label
        ),
        Chapter(
            name = R.string.chapter_name_tolerance,
            description = R.string.chapter_description_tolerance,
            difficulty = ChapterDifficulty.Medium,
            tag = ChapterTag.Tolerance,
            chapterScreenNum = 4,
            startChapterButtonLabel = R.string.chapter_tolerance_start_chapter_button_label
        ),
        Chapter(
            name = R.string.chapter_name_hedonic_circuit,
            description = R.string.chapter_description_hedonic_circuit,
            difficulty = ChapterDifficulty.Hard,
            tag = ChapterTag.HedonicCircuit,
            chapterScreenNum = 4,
            startChapterButtonLabel = R.string.chapter_hedonic_circuit_start_chapter_button_label
        ),
        Chapter(
            name = R.string.chapter_name_solution,
            description = R.string.chapter_description_solution,
            difficulty = ChapterDifficulty.Easy,
            tag = ChapterTag.Solutions,
            chapterScreenNum = 5,
            startChapterButtonLabel = R.string.chapter_solution_start_chapter_button_label
        )
    )
}
