package wipro.whetherfrecast.main.ui.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wipro.whetherfrecast.main.network.ApiServiceInterface
import wipro.whetherfrecast.main.network.ResponseCallBack
import wipro.whetherfrecast.main.ui.model.WeatherResponse
import wipro.whetherfrecast.main.utils.UrlConstants

/**
 * Model to make the api call
 */
class WeatherRepository {

    fun getWeatherListForCity(cityName: String, callback: ResponseCallBack<WeatherResponse>) {

        /**
         * NOW GET THE DETAILS FROM API
         */
        val call = ApiServiceInterface.create().getWeatherDetails(cityName, UrlConstants.APP_KEY)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                var res = response.body()?.let {
                    if (it.cod == "200") {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Server Error")
                    }
                    1
                }

                if (res != 1) {
                    callback.onError("Error")
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("ERROR", call.toString())
                callback.onError(call.toString())
            }
        })
    }
}