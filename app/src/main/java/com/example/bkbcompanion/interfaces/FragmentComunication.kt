package com.example.bkbcompanion.interfaces

import com.example.bkbcompanion.models.BKBViewModel

interface FragmentComunication {
    fun addMatch()
    fun viewMatches()
    fun sendData(pos: Int)
}