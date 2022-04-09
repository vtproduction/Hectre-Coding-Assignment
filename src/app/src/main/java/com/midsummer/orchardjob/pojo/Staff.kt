package com.midsummer.orchardjob.pojo


import com.google.gson.annotations.SerializedName

data class Staff(
    @SerializedName("id")
    val id: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String
)