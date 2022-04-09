package com.midsummer.orchardjob.pojo


import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("rowId")
    val rowId: Int,
    @SerializedName("totalTrees")
    val totalTrees: Int,
    @SerializedName("current")
    val current: Int,
    @SerializedName("completed")
    val completed: CompletedJob
)