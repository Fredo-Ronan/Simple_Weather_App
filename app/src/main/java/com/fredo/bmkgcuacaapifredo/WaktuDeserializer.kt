package com.fredo.bmkgcuacaapifredo

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class WaktuDeserializer : JsonDeserializer<Waktu> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Waktu {
        if(json == null || !json.isJsonObject) {
            return Waktu("", "", "")
        }

        val jsonObject = json.asJsonObject

        val intervalName = when {
            jsonObject.has("day") -> "day"
            jsonObject.has("h") -> "h"
            else -> ""
        }

        val typeWaktu = jsonObject.get("type").asString
        val intervalWaktu = jsonObject.get(intervalName).asString
        val datetime = jsonObject.get("datetime").asString

        return Waktu(typeWaktu, intervalWaktu, datetime)
    }
}