package com.midsummer.orchardjob.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midsummer.orchardjob.pojo.FieldConfig
import com.midsummer.orchardjob.pojo.OrchardJob

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Dao
interface OrchardJobDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<OrchardJob>)

    @Query("SELECT * FROM OrchardJob")
    suspend fun getAllJobs() : List<OrchardJob>

    @Query("SELECT * FROM OrchardJob WHERE type = 1")
    suspend fun getPruningJobs() : List<OrchardJob>

    @Query("SELECT * FROM OrchardJob WHERE type = 2")
    suspend fun getThinningJobs() : List<OrchardJob>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFieldConfig(data: List<FieldConfig>)

    @Query("SELECT * FROM FieldConfig")
    suspend fun getAllFieldConfig() : List<FieldConfig>

    @Query("SELECT * FROM FieldConfig WHERE rowId = :rowId LIMIT 1")
    suspend fun getRow(rowId: Int) : FieldConfig?

}