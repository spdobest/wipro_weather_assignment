package wipro.whetherfrecast.main.ui.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) : BaseObservable(), Parcelable {

    @get:Bindable
    var firstName: String = "SIba"
        set(value) {
            field = main
            notifyPropertyChanged(BR.firstName)
        }
}