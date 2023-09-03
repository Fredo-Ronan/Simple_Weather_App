package com.fredo.bmkgcuacaapifredo

import com.google.gson.annotations.SerializedName

data class Wilayah(
    @SerializedName("provinsi") val provinsi: String,
    @SerializedName("area") val area: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("arrayOfForecast") val arrayOfForecast: List<Forecast>
)