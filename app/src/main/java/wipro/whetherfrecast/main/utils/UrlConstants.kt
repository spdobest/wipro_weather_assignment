package wipro.whetherfrecast.main.utils

interface UrlConstants {
    val BASE_URL: String
        get() = "http://api.openweathermap.org/data/2.5/"
    val IMG_URL: String
        get() = "http://openweathermap.org/img/w/"

    companion object {
        val APP_KEY: String = "7726cc18a2f0b0e79676dc02fdf5fa69"
    }

}