package com.sogya.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sogya.data.models.ServerState
@Dao
interface ServersDao {

    @Query("SELECT * FROM servers")
    fun getAll(): LiveData<ServerState>

    @Query("SELECT * FROM servers WHERE serverId IN(:serverId)")
    fun getById(serverId: Int): LiveData<ServerState>

    @Insert(onConflict = REPLACE)
    fun insert(serverState: ServerState)

    @Delete
    fun delete(serverState: ServerState)
}