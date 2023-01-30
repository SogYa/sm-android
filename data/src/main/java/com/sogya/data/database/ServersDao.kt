package com.sogya.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sogya.data.models.ServerState

@Dao
interface ServersDao {

    @Query("SELECT * FROM servers")
    fun getAll(): LiveData<List<ServerState>>

    @Query("SELECT * FROM servers WHERE serverUri IN(:serverUri)")
    fun getById(serverUri: String): ServerState

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(serverState: ServerState)

    @Query("DELETE FROM servers WHERE serverUri IN (:serverId)")
    fun delete(serverId: String)

    @Update
    fun update(serverState: ServerState)
}