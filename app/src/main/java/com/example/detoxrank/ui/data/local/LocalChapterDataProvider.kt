package com.example.detoxrank.ui.data.local

import com.example.detoxrank.ui.data.Chapter
import com.example.detoxrank.ui.data.ChapterDifficulty
import com.example.detoxrank.ui.data.ChapterTag

object LocalChapterDataProvider {
    val allChapters = mutableListOf(
        Chapter(
            name = "Introduction",
            description = "Welcome to dopamine detox theory! Here you will find some motivation for" +
                    " why you should study this section.",
            difficulty = ChapterDifficulty.Easy,
            tag = ChapterTag.Introduction,
            chapterScreenNum = 3
        ),
        Chapter(
            name = "Dopamine",
            description = "In this chapter, we will talk about how neurons and neurotransmitters work. We also get a brief look into what dopamine does in the brain and" +
                    " how it influences our motivation.",
            difficulty = ChapterDifficulty.Medium,
            tag = ChapterTag.Dopamine,
            chapterScreenNum = 5
        ),
        Chapter(
            name = "Reinforcement",
            description = "This chapter looks deeper into brain\'s reward circuitry and explores the main behavior that this circuit executes - reinforcement learning.",
            difficulty = ChapterDifficulty.Hard,
            tag = ChapterTag.Reinforcement,
            chapterScreenNum = 5
        ),
        Chapter(
            name = "Tolerance",
            description = "Here we will talk about tolerance which is developed in the brain when exposing yourself to a large quantum of dopamine.",
            difficulty = ChapterDifficulty.Medium,
            tag = ChapterTag.Tolerance,
            chapterScreenNum = 4
        ),
        Chapter(
            name = "Hedonic circuit",
            description = "Let\'s learn about the hedonic circuit which is responsible for producing enjoyment in the brain!",
            difficulty = ChapterDifficulty.Hard,
            tag = ChapterTag.HedonicCircuit,
            chapterScreenNum = 4
        ),
        Chapter(
            name = "Solution - Detox",
            description = "The chapter talks about how to do a dopamine detox correctly.",
            difficulty = ChapterDifficulty.Easy,
            tag = ChapterTag.Solutions,
            chapterScreenNum = 5
        )
    )
}
