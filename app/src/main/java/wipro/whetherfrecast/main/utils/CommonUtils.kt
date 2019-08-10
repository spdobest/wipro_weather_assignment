package wipro.whetherfrecast.main.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object {
        fun getDateInddMMMYYYYFormat(weatherDate: String = ""): String {

            var formattedDate = ""

            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetFormat = SimpleDateFormat("dd MMM yyyy")

            val date: Date
            try {
                date = originalFormat.parse("2013-02-27 21:06:30")
                formattedDate = targetFormat.format(date)
                System.out.println("Old Format :   " + originalFormat.format(date))
                System.out.println("New Format :   " + targetFormat.format(date))

            } catch (ex: Exception) {
                ex.stackTrace
            }
            return formattedDate
        }

        fun getDateInddHHmmFormat(weatherDate: String = ""): String {

            var formattedTime = ""

            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetFormat = SimpleDateFormat("hh:mm aa")

            val date: Date
            try {
                date = originalFormat.parse("2013-02-27 21:06:30")
                System.out.println("Old Format :   " + originalFormat.format(date))
                System.out.println("New Format :   " + targetFormat.format(date))
                formattedTime = targetFormat.format(date)
            } catch (ex: ParseException) {
                ex.stackTrace
            }
            return formattedTime
        }

        fun getDayOftheWeek(weatherDate: String = ""): String {

            var formattedDay = ""

            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetFormat = SimpleDateFormat("EEEE")

            val date: Date
            try {
                date = originalFormat.parse("2013-02-27 21:06:30")
                System.out.println("Old Format :   " + originalFormat.format(date))
                System.out.println("New Format :   " + targetFormat.format(date))
                formattedDay = targetFormat.format(date)
            } catch (ex: ParseException) {
                ex.stackTrace
            }
            return formattedDay
        }
        fun isNetworkAvailable(activity: AppCompatActivity):Boolean{
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }
}