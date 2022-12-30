package com.sogya.data.database

import androidx.room.*
import com.sogya.data.models.State
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface StateDao {

    @Query("SELECT * FROM states")
    fun getAll(): Flowable<List<State>>

    @Query("SELECT * FROM states WHERE entityId IN(:entityId)")
    fun getState(entityId: String): Single<List<State>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(states: List<State>)

    @Query("DELETE FROM states WHERE entityId IN(:stateId)")
    fun delete(stateId: String)
}