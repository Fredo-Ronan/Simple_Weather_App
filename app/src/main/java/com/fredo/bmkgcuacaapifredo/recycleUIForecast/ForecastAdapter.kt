package com.fredo.bmkgcuacaapifredo.recycleUIForecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fredo.bmkgcuacaapifredo.R

class ForecastAdapter(
    private val forecastList : ArrayList<ForecastUI>
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var locationArea : TextView
        var forecastWeather : TextView
        var temperatureForecast : TextView
        var time : TextView

        init {
            locationArea = itemView.findViewById(R.id.areaText)
            forecastWeather = itemView.findViewById(R.id.cuacaText)
            temperatureForecast = itemView.findViewById(R.id.temperatureText)
            time = itemView.findViewById(R.id.waktuText)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_forecast_item, parent, false)

        return ForecastViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = forecastList[position]
        holder.locationArea.text = currentItem.area
        holder.forecastWeather.text = currentItem.weatherForecast
        holder.temperatureForecast.text = currentItem.temperature
        holder.time.text = currentItem.time
    }
}