package com.example.fleekpeek.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalManager {

    suspend fun saveAppEntry()

    suspend fun readAppEntry(): Flow<Boolean>

}