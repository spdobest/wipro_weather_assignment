package wipro.whetherfrecast.main.listeners

import wipro.whetherfrecast.main.ui.model.WeatherDetails

interface WeatherClickListener {
    fun onWeatherClick(weatherDetails: WeatherDetails)
}