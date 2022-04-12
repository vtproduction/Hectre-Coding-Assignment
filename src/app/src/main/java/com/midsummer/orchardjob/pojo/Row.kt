package com.midsummer.orchardjob.pojo


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Row(
    @SerializedName("rowId")
    val rowId: Int,
    @SerializedName("totalTrees")
    var totalTrees: Int,
    @SerializedName("current")
    var current: Int,
    @SerializedName("completed")
    val completed: CompletedJob?
): Serializable