package com.sogya.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sogya.data.models.State

@Dao
interface StateDao {

    @Query("SELECT * FROM states WHERE ownerId IN(:serverUri)")
    fun getAllByServerId(serverUri: String): LiveData<List<State>>

    @Query("SELECT * FROM states WHERE entityId IN(:entityId)")
    fun getState(entityId: String): State

    @Query("SELECT * FROM states WHERE entityId IN(:entityId)")
    fun getStateLiveData(entityId: String): LiveData<State>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(states: List<State>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(state: State)

    @Query("DELETE FROM states WHERE entityId IN(:stateId)")
    fun delete(stateId: String)

    @Query("SELECT EXISTS (SELECT 1 FROM states WHERE entityId = :stateId)")
    fun isStateExist(stateId: String): Boolean

    @Update
    fun updateState(state: State)

    @Update
    fun updateStates(state: List<State>)

    @Query("UPDATE states SET groupId = -1  WHERE groupId IN(:stateGroupId)")
    fun deleteGroupIdFromStets(stateGroupId: Int)

    @Query("SELECT * FROM states WHERE groupId IN(:groupId) ")
    fun getAllByGroup(groupId: Int): LiveData<List<State>>
}