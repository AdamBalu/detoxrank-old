package com.example.detoxrank.data.chapter

import kotlinx.coroutines.flow.Flow

interface ChaptersRepository {
    fun getChapterByName(name: String): Flow<Chapter?>
    fun getChapterById(id: Int): Flow<Chapter?>
    suspend fun insertChapter(chapter: Chapter)
    suspend fun deleteChapter(chapter: Chapter)
    suspend fun updateChapter(chapter: Chapter)
    fun getAllChapters(): Flow<List<Chapter>>
}
