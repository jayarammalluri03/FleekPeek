package com.example.fleekpeek.domain.use_cases.app_entry_useCase

import com.example.fleekpeek.domain.manager.LocalManager

class SaveEntry(private val localManager: LocalManager) {

    suspend operator fun invoke (){
        localManager.saveAppEntry()
    }
}