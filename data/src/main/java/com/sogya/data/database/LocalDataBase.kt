package com.sogya.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sogya.data.models.State

@Database(entities = [State::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun stateDao(): StateDao
}