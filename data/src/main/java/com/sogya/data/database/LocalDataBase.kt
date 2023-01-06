package com.sogya.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sogya.data.models.ServerState
import com.sogya.data.models.State
import com.sogya.data.models.StateGroup

@Database(entities = [State::class, ServerState::class, StateGroup::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun stateDao(): StateDao
    abstract fun serverDao(): ServersDao
    abstract fun groupDao(): GroupDao
}