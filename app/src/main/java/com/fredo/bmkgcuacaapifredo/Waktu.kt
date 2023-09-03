package com.fredo.bmkgcuacaapifredo

import com.google.gson.annotations.SerializedName

data class Waktu(
    @SerializedName("type") val type: String,
    val interval: String,
    @SerializedName("datetime") val datetime: String
)