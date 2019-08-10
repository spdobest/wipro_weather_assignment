package wipro.whetherfrecast.main.ui.model


data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherDetails>,
    val message: Double
)












