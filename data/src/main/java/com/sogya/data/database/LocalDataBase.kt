package com.sogya.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sogya.data.database.daos.GroupDao
import com.sogya.data.database.daos.ServersDao
import com.sogya.data.database.daos.StateDao
import com.sogya.data.database.daos.ZoneDao
import com.sogya.data.models.ServerState
import com.sogya.data.models.StateData
import com.sogya.data.models.StateGroup
import com.sogya.data.models.ZoneData

@Database(
    entities = [StateData::class, ServerState::class, StateGroup::class, ZoneData::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun stateDao(): StateDao
    abstract fun serverDao(): ServersDao
    abstract fun groupDao(): GroupDao
    abstract fun zoneDao(): ZoneDao
}