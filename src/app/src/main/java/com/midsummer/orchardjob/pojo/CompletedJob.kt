package com.midsummer.orchardjob.pojo


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompletedJob(
    @SerializedName("staff")
    val staff: Staff,
    @SerializedName("completed")
    val completed: Int
): Serializable