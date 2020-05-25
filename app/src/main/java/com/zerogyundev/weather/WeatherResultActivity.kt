package com.zerogyundev.weather

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.city_item.*
import kotlinx.android.synthetic.main.weather_result.*
import kotlinx.android.synthetic.main.weather_result.weatherImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class WeatherResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_result)
        var city =intent.getStringExtra("City")
        setTitle(city);
        cityName_result.text = city
        contentMain.visibility = View.INVISIBLE
//        cityName_result.visibility = View.INVISIBLE
//        weatherImage.visibility = View.INVISIBLE
//        degree_result.visibility = View.INVISIBLE
        loadingCircle.visibility = View.VISIBLE

//        println(city)
        var city_code = 0
        if (city === "Seoul"){
            city_code = 1835847
        } else if (city == "Daegu") {
            city_code = 1835327
        } else if (city == "Busan") {
            city_code = 1838524
        } else if (city == "Daejeon") {
            city_code = 1835235
        } else {
            city_code = 1835847 //Default as Seoul
        }
        var parse_reslut: MutableList<String> = arrayListOf()
        GlobalScope.launch {
            var job = async {
                var res = getData(city_code.toString())
                res
            }
            var dataFromServer = job.await()
            println(dataFromServer.toString())
            val weather_result = dataFromServer.getJSONArray("weather").getJSONObject(0).get("main")
            val detail_info = dataFromServer.getJSONObject("main")
            val current = detail_info.get("temp").toString()
            val feels_like = detail_info.get("feels_like").toString()
            val temp_min = detail_info.get("temp_min").toString()
            val temp_max = detail_info.get("temp_max").toString()
            val humidity = detail_info.get("humidity").toString()
            val pressure = detail_info.get("pressure").toString()

            val weatherData = dataFromServer.getJSONArray("weather").getJSONObject(0).get("icon").toString()
            val imageUrl = "http://openweathermap.org/img/wn/$weatherData@2x.png"
            println(imageUrl)


            parse_reslut.addAll(listOf(city, current, feels_like, temp_max, temp_min, humidity, pressure))
            println(parse_reslut)

            runOnUiThread {
                changeImage(imageUrl)
                changeResultText(parse_reslut)

            }


        }




    }

    fun changeResultText(result: MutableList<String>) {
//        city, current, feels_like, temp_max, temp_min, humidity, pressure
        cityName_result.text = result[0]
        degree_result.text = "${result[1]}°C"
        feelsLikeResult.text = "${result[2]}°C"
        max.text = "${result[3]}°C"
        min.text = "${result[4]}°C"
        humidity.text = "${result[5]}%"
        pressure.text = "${result[6]}mBar"


        loadingCircle.visibility = View.GONE
//        cityName_result.visibility = View.VISIBLE
//        weatherImage.visibility = View.VISIBLE
//        degree_result.visibility = View.VISIBLE
        contentMain.visibility = View.VISIBLE

        //@TODO : Add image from web using glide
        //@TODO : Add detailed info layout like humidity, min/max degree etc.

    }

    fun changeImage(url: String){
        Glide
            .with(this).load(url).into(weatherImage)
    }


    fun getData(city_code: String): JSONObject {
        val data = JSONObject()

        println("City Code : $city_code")
        val url =
            "http://api.openweathermap.org/data/2.5/weather?id=$city_code&appid=639f67ffd4c757ee0122d5bc5615d1df&units=metric"
        println(url)
        val (request, response, result) = url
            .httpGet()
            .responseString()

        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println("ERROR!!!!")
                println(ex)
            }
            is Result.Success -> {
                println("Success!!")
                val data = result.get()

                return JSONObject(data)

            }

        }
        return data
    }

}