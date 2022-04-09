package com.midsummer.orchardjob.pojo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "OrchardJob")
data class OrchardJob(

    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("orchard")
    val orchard: String,
    @SerializedName("block")
    val block: String,
    @SerializedName("staff")
    val staff: Staff,
    @SerializedName("field")
    val row: List<Row>
)