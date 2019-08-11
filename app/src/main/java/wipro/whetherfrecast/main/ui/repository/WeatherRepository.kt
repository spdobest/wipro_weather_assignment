package wipro.whetherfrecast.main.ui.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wipro.whetherfrecast.main.network.ApiServiceInterface
import wipro.whetherfrecast.main.network.ResponseCallBack
import wipro.whetherfrecast.main.ui.model.WeatherResponse


class WeatherRepository(var apiServiceInterface: ApiServiceInterface) {

    fun getWeatherListForCity(cityName: String, callback: ResponseCallBack<WeatherResponse>) {

        /**
         * NOW GET THE DETAILS FROM API
         */
        val call = apiServiceInterface.getWeatherDetails(cityName, "7726cc18a2f0b0e79676dc02fdf5fa69")
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    if (it.cod == "200") {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Server Error")
                    }
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("ERROR", call.toString())
                callback.onError(call.toString())
            }
        })
    }
}