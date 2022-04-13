package com.midsummer.orchardjob.pojo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    var staff: Staff,
    @SerializedName("field")
    var row: List<Row>
) : Serializable