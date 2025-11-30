package com.example.fleekpeek.domain.use_cases.app_entry_useCase

import com.example.fleekpeek.domain.manager.LocalManager
import kotlinx.coroutines.flow.Flow

class ReadEntry(private val localUserManager: LocalManager) {

    suspend operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}