package com.example.bkbcompanion.models

import android.app.Application
import androidx.lifecycle.*
import com.example.bkbcompanion.database.BKBRoomDatabase
import com.example.bkbcompanion.database.entities.Match
import com.example.bkbcompanion.repository.BKBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class BKBViewModel(app: Application): AndroidViewModel(app) {
    val repository: BKBRepository
    val database: BKBRoomDatabase
    var getAllMatches: LiveData<List<Match>>
    lateinit var currentMatch: MediatorLiveData<Match>

    init {
        database = BKBRoomDatabase.getDatabase(app, viewModelScope)
        val matchDao = database.matchDao()
        repository = BKBRepository(matchDao)
        getAllMatches = repository.getAllMatches
        currentMatch = MediatorLiveData()
        currentMatch.value = Match(0,"A", "B", 0, 0, Calendar.getInstance().time, false)
    }

    fun insertMatch(match: Match) =  viewModelScope.launch(Dispatchers.IO){
        repository.insertMatch(match)
    }

    fun updateMatch(match: Match) =  viewModelScope.launch(Dispatchers.IO){
        repository.updateMatch(match)
    }

    fun startNewMatch(newMatch: Match){
        currentMatch.value = newMatch
    }
}