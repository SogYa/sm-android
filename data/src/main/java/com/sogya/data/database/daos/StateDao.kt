package com.sogya.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sogya.data.models.StateData

@Dao
interface StateDao {

    @Query("SELECT * FROM states WHERE ownerId IN(:serverUri)")
    fun getAllByServerId(serverUri: String): LiveData<List<StateData>>

    @Query("SELECT * FROM states WHERE entityId IN(:entityId)")
    fun getState(entityId: String): StateData

    @Query("SELECT * FROM states WHERE entityId IN(:entityId)")
    fun getStateLiveData(entityId: String): LiveData<StateData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stateData: List<StateData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(stateData: StateData)

    @Query("DELETE FROM states WHERE entityId IN(:stateId)")
    fun delete(stateId: String)

    @Query("SELECT EXISTS (SELECT 1 FROM states WHERE entityId = :stateId)")
    fun isStateExist(stateId: String): Boolean

    @Update
    fun updateState(stateData: StateData)

    @Update
    fun updateStates(stateData: List<StateData>)

    @Query("UPDATE states SET groupId = -1  WHERE groupId IN(:stateGroupId)")
    fun deleteGroupIdFromStets(stateGroupId: Int)

    @Query("SELECT * FROM states WHERE groupId IN(:groupId) ")
    fun getAllByGroup(groupId: Int): LiveData<List<StateData>>
}