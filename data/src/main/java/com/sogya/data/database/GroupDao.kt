package com.sogya.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sogya.data.models.StateGroup

@Dao
interface GroupDao {

    @Query("SELECT * FROM stategroup WHERE ownerId IN(:ownerId)")
    fun getAllByOwner(ownerId: String): LiveData<List<StateGroup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(stateGroup: StateGroup)

    @Query("DELETE FROM stategroup WHERE groupId IN (:groupId)")
    fun deleteGroup(groupId: Int)
}