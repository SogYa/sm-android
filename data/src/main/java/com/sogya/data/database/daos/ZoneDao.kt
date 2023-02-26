package com.sogya.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sogya.data.models.ZoneData

@Dao
interface ZoneDao {

    @Query("SELECT * FROM zones")
    fun getAllZones(): LiveData<List<ZoneData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertZone(zoneData: ZoneData)

    @Delete
    fun deleteZone(zoneData: ZoneData)
}