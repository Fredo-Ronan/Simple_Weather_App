package com.fredo.bmkgcuacaapifredo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fredo.bmkgcuacaapifredo.databinding.ActivityMainBinding
import com.fredo.bmkgcuacaapifredo.recycleUIForecast.ForecastAdapter
import com.fredo.bmkgcuacaapifredo.recycleUIForecast.ForecastUI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.InputStreamReader

import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvWeather: RecyclerView

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MAIN ENTRY APP
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        fetchAvailableLocation().start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_right_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.github -> {
                val openURL = Intent(android.content.Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://github.com/Fredo-Ronan")
                startActivity(openURL)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FETCHING DATA THREAD SIDE
    private fun fetchWeatherData(location: String): Thread{
        return Thread{
            val url = URL("https://fredo-ronan-api.cyclic.app/api/bmkg-cuaca-api?location=$location")
            val connection = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200){
                println("Connected Successfully")

                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")

                val gson = GsonBuilder()
                    .registerTypeAdapter(Waktu::class.java, WaktuDeserializer())
                    .create()

                val response = gson.fromJson(inputStreamReader, ResponseWeather::class.java)


                val testForecastList = response.getForecast()

                setRecyclerViewUI(testForecastList)

            } else {
                val error = "Error when fetching weather data in location $location"
                binding.errorHandling.text = error
            }
        }
    }

    private fun fetchAvailableLocation(): Thread {
        return Thread {
            val url = URL("https://fredo-ronan-api.cyclic.app/api/list-wilayah")
            val connection = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200){
                println("Connected Successfully")
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val result = Gson().fromJson(inputStreamReader, ListWilayah::class.java)

                setAvailableLocationUI(result)
                inputStreamReader.close()
                inputSystem.close()
            } else {
                println("Failed to connect to API server")
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UI OPERATION THREAD SIDE
    private fun setAvailableLocationUI(fetchedListWilayah: ListWilayah) {
        runOnUiThread {
            kotlin.run {
                val spinner: Spinner = findViewById(R.id.spinner)

                val choices = fetchedListWilayah.message

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, choices)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = adapter

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long){
                        val selectedItem = parent.getItemAtPosition(position).toString()

                        println("Selected $selectedItem")

                        fetchWeatherData(selectedItem).start()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }
        }
    }

    private fun setRecyclerViewUI(forecastUIList: ArrayList<ForecastUI>){
        runOnUiThread {
            kotlin.run {
                val layoutManager = LinearLayoutManager(this)
                val adapter : ForecastAdapter = ForecastAdapter(forecastUIList)

                rvWeather = findViewById(R.id.weatherView)
                rvWeather.layoutManager = layoutManager
                rvWeather.setHasFixedSize(true)
                rvWeather.adapter = adapter
            }
        }
    }

}