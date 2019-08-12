package wipro.whetherfrecast.main.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round


/**
 * class where all the common methods are written here
 * where You will get commonly used methods in the application
 */
class CommonUtils {

    companion object {
        /**
         * Params : date: String in yyyy-MM-dd HH:mm:ss
         * It convert date to dd MM YYYY format
         * 2013-02-27 21:06:30 - 27 Feb 2013
         *
         * Default Date parameter is 2019-08-15 21:06:30
         */
        fun getDateInddMMMYYYYFormat(weatherDate: String = "2019-08-15 21:06:30"): String {

            var formattedDate = ""

            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetFormat = SimpleDateFormat("dd MMM yyyy")

            val date: Date
            try {
                date = originalFormat.parse(weatherDate)
                formattedDate = targetFormat.format(date)

            } catch (ex: Exception) {
                ex.stackTrace
            }
            return formattedDate
        }

        /**
         * Params : date: String in yyyy-MM-dd HH:mm:ss
         * It convert date to dd hh:mm aa format
         * 2013-02-27 21:06:30 - 9:06 PM
         *
         * Default Date parameter is 2019-08-15 21:06:30
         */
        fun getDateInddHHmmFormat(weatherDate: String = "2019-08-15 21:06:30"): String {

            var formattedTime = ""

            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetFormat = SimpleDateFormat("hh:mm aa")

            val date: Date
            try {
                date = originalFormat.parse(weatherDate)
                formattedTime = targetFormat.format(date)
            } catch (ex: ParseException) {
                ex.stackTrace
            }
            return formattedTime
        }

        /**
         * Params : date: String in yyyy-MM-dd HH:mm:ss
         * It convert date to EEEE format
         * 2013-02-27 21:06:30 - MONDAY
         *
         * Default Date parameter is 2019-08-15 21:06:30
         */
        fun getDayOftheWeek(weatherDate: String = "2019-08-15 21:06:30"): String {

            var formattedDay = ""

            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetFormat = SimpleDateFormat("EEEE")

            val date: Date
            try {
                date = originalFormat.parse(weatherDate)
                formattedDay = targetFormat.format(date)
            } catch (ex: ParseException) {
                ex.stackTrace
            }
            return formattedDay
        }

        /**
         * Check internet connection
         */
        fun isNetworkAvailable(activity: AppCompatActivity):Boolean{
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

        fun getCelciousFromKalvin(tempInKalvin: Double): String {
            val tempInCelcious: Double = round((tempInKalvin - 273.15) * 100) / 100
            return tempInCelcious.toString() + 0x00B0.toChar() + " C"
        }
    }
}