package com.example.detoxrank.ui.data.local

import com.example.detoxrank.ui.data.Chapter
import com.example.detoxrank.ui.data.ChapterDifficulty
import com.example.detoxrank.ui.data.ChapterTag

object LocalChapterDataProvider {
    val allChapters = mutableListOf(
        Chapter(
            name = "Dopamine",
            description = "In this chapter, we will talk about basic brain parts and influence of dopamine on " +
                    "the behavior and motivation",
            difficulty = ChapterDifficulty.Hard,
            tag = ChapterTag.Dopamine
        ),
        Chapter(
            name = "Hedonics vs Dopaminergics",
            description = "This chapter focuses on the main problems of hijacked dopamine reward circuitry " +
                    "and differences between liking and wanting",
            difficulty = ChapterDifficulty.Hard,
            tag = ChapterTag.HedonicsVsDopaminergics
        ),
        Chapter(
            name = "Reinforcement",
            description = "Here we will talk about reinforcement behaviors and how the addiction is created",
            difficulty = ChapterDifficulty.Easy,
            tag = ChapterTag.Reinforcement
        ),
        Chapter(
            name = "PREP",
            description = "This chapter talks about prediction reward error processing - a phenomena which " +
                    "happens while brain calculates and predicts a reward.",
            difficulty = ChapterDifficulty.Medium,
            tag = ChapterTag.PREP
        ),
        Chapter(
            name = "Solutions",
            description = "The chapter talks about why dopamine detoxing is an important thing to do if we have " +
                    "a hijacked circuit, and focuses on details about detoxing",
            difficulty = ChapterDifficulty.Medium,
            tag = ChapterTag.Solutions
        )
    )
}