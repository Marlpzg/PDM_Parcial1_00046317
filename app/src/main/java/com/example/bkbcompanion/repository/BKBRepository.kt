package com.example.bkbcompanion.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.bkbcompanion.database.daos.MatchDAO
import com.example.bkbcompanion.database.entities.Match

class BKBRepository(private val matchDao: MatchDAO) {

    val getAllMatches: LiveData<List<Match>> = matchDao.getAllMatches()

    @WorkerThread
    suspend fun insertMatch(match: Match){
        matchDao.insert(match)
    }

    @WorkerThread
    suspend fun updateMatch(match: Match){
        matchDao.updateMatch(match)
    }

}