package com.fredo.bmkgcuacaapifredo

import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("_") val nilai: String,
    @SerializedName("$") val unit: Unit
)