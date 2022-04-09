package com.midsummer.orchardjob.pojo


import com.google.gson.annotations.SerializedName

data class CompletedJob(
    @SerializedName("staff")
    val staff: Staff,
    @SerializedName("completed")
    val completed: Int
)