package com.example.bkbcompanion.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bkbcompanion.database.converters.Converters
import com.example.bkbcompanion.database.daos.MatchDAO
import com.example.bkbcompanion.database.entities.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Match::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BKBRoomDatabase: RoomDatabase() {

    abstract fun matchDao(): MatchDAO

    companion object {
        @Volatile
        private var INSTANCE: BKBRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BKBRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, BKBRoomDatabase::class.java, "Bkb_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    /*private class BKBDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    authorPopulateDatabase(database.authorDAO())
                    bookPopulateDatabase(database.bookDAO())
                    tagPopulateDatabase(database.tagDAO())
                    authorBookPopulate(database.authorBookJoinDAO())
                    tagBookPopulate(database.tagBookJoinDAO())
                }
            }
        }

    }*/

}