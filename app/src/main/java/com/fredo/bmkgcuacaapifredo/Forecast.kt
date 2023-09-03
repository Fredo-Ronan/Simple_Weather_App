package com.fredo.bmkgcuacaapifredo

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("tipePerkiraan") val tipePerkiraan: String,
    @SerializedName("tipeWaktuPerkiraan") val tipeWaktuPerkiraan: String,
    @SerializedName("arrayOfTimeRange") val arrayOfTimeRange: List<TimeRange>
)