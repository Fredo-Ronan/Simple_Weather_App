package com.fredo.bmkgcuacaapifredo

import com.google.gson.annotations.SerializedName

data class TimeRange(
    @SerializedName("waktu") val waktu: Waktu,
    @SerializedName("value") val value: List<Value>
)