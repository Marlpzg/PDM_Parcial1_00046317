package com.example.bkbcompanion.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bkbcompanion.database.entities.Match

@Dao
interface MatchDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match:Match)

    @Query("SELECT * FROM matches ORDER BY date DESC")
    fun getAllMatches(): LiveData<List<Match>>

    @Update
    suspend fun updateMatch(match: Match)

}