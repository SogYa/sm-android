package com.sogya.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sogya.data.models.State
import com.sogya.domain.models.StateDomain
import io.reactivex.Single

@Dao
interface StateDao {

    @Query("SELECT * FROM states")
    fun getAll(): Single<List<State>>

    @Query("SELECT * FROM states WHERE entityId IN(:entityId)")
    fun getState(entityId: String): Single<List<State>>

    @Insert
    fun insert(vararg state: State)

    @Delete
    fun delete(state: State)
}