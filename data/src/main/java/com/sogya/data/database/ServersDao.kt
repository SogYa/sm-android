package com.sogya.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
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

    @Update
    fun update(serverState: ServerState)
}