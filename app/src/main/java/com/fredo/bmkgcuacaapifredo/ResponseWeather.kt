package com.fredo.bmkgcuacaapifredo

import android.os.Build
import com.fredo.bmkgcuacaapifredo.recycleUIForecast.ForecastUI
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ResponseWeather(
    val author: String,
    val message: List<Wilayah>
) {

    fun getForecast(): ArrayList<ForecastUI> {

        val forecastList = arrayListOf<ForecastUI>()
        val currentTime = getCurrentHour24Format()
        var weatherCode: String = ""
        var temperatureValue: String = ""

        for((indexForecastList, wilayah) in message.withIndex()){

            for(forecast in wilayah.arrayOfForecast){
                if(forecast.tipePerkiraan.compareTo("Weather") == 0){
                    for(time in forecast.arrayOfTimeRange){
                        if(time.waktu.type.compareTo("hourly") == 0){
                            if(Integer.parseInt(time.waktu.interval) >= Integer.parseInt(currentTime)
                                && Integer.parseInt(currentTime) <= 23){
                                for(kodeCuaca in time.value){
                                    weatherCode = getWeatherStringFromCode(kodeCuaca.nilai)
                                }
                            }
                        }
                    }
                } else if (forecast.tipePerkiraan.compareTo("Temperature") == 0){
                    for(time in forecast.arrayOfTimeRange){
                        if(time.waktu.type.compareTo("hourly") == 0){
                            if(Integer.parseInt(time.waktu.interval) >= Integer.parseInt(currentTime)
                                && Integer.parseInt(currentTime) <= 23){
                                for(suhu in time.value){
                                    if(suhu.unit.unit.compareTo("C") == 0){
                                        temperatureValue = suhu.nilai + " C"
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val currentLocationForecast = ForecastUI(wilayah.area, weatherCode, temperatureValue, currentTime)
            forecastList.add(indexForecastList, currentLocationForecast)

        }

        return forecastList
    }


    private fun getCurrentHour24Format(): String {
        val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatter = DateTimeFormatter.ofPattern("HH")
        return currentTime.format(formatter)
    }

    private fun getWeatherStringFromCode(code: String): String {
        val codes = mapOf(
            "0" to "Cerah",
            "1" to "Cerah Berawan",
            "2" to "Cerah Bwrawan",
            "3" to "Berawan",
            "4" to "Berawan Tebal",
            "5" to "Udara Kabur",
            "10" to "Asap",
            "45" to "Kabut",
            "60" to "Hujan Ringan",
            "61" to "Hujan Sedang",
            "63" to "Hujan Lebat",
            "80" to "Hujan Lokal",
            "95" to "Hujan Petir",
            "97" to "Hujan Petir"
        )

        return codes[code] ?: ""
    }
}