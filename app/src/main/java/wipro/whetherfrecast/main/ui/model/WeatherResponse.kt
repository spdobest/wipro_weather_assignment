package wipro.whetherfrecast.main.ui.model

import androidx.databinding.BaseObservable


data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherDetails>,
    val message: Double
) : BaseObservable()












