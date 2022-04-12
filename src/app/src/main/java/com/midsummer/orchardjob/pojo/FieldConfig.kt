package com.midsummer.orchardjob.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by nienle on 12,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Entity(tableName = "FieldConfig")
data class FieldConfig(

    @PrimaryKey
    @SerializedName("rowId")
    val rowId: Int,
    @SerializedName("totalTrees")
    val totalTrees: Int
): Serializable