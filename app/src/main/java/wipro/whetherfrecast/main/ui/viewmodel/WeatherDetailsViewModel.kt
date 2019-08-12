package wipro.whetherfrecast.main.ui.viewmodel

import wipro.whetherfrecast.main.ui.model.Rain
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import kotlin.math.round


class WeatherDetailsViewModel(val weatherDetails: WeatherDetails, val city: String) :
    WeatherItemViewModel(weatherDetails) {

    var rainChild: Rain

    init {
        rainChild = weatherDetails.rain
    }

    fun getWIndDetails(): String {
        var windDetails = "--"
        wind.let {
            windDetails =
                "Wind Will blow at " + it.speed.toString() + " m/sec SPEED " + "\n IN  " + round((it.deg * 100) / 100).toString() + 0x00B0.toChar() + " Direction"
        }
        return windDetails
    }

    fun getOtherDetails(): String {
        var otherDetails = ""
        main.let {
            otherDetails =
                "Pressure  ${it.pressure}hPa \n Sea Level Pressure ${it.sea_level} hPa \nGround Level Pressure ${it.grnd_level} \n Humidity ${it.humidity} %"
        }
        return otherDetails
    }

    fun getTemperatureDetails(): String {
        var tempeture = ""
        main.let {
            tempeture =
                "Highest Tempereture " + it.temp_min.toString() + 0x00B0.toChar() + "\n Lowest Temperature " + it.temp_min.toString() + 0x00B0.toChar()
        }
        return tempeture
    }

    fun weatherToolBarTitle(): String {
        var weatherDescription = "--"
        weather.let {
            weatherDescription = city
        }
        return weatherDescription
    }

    fun getRain(): String {
        var rainStr = "--"
        var volume = "5"//rainChild?.`3h`.toString()
        weather.let {
            rainStr = "${it.description} \nPrecipitation volume \n${volume} mm"
        }
        return rainStr
    }
}