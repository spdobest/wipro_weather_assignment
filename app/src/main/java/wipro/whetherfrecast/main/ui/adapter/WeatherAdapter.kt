package wipro.whetherfrecast.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import wipro.whetherfrecast.main.databinding.ItemviewWeatherBinding
import wipro.whetherfrecast.main.ui.adapter.viewholders.WeatherViewHolder
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.viewmodel.WeatherItemViewModel
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel
import wipro.whetherfrecast.main.utils.WeatherDiffUtilsCallBack


class WeatherAdapter(var listWeather: ArrayList<WeatherDetails>) : RecyclerView.Adapter<WeatherViewHolder>() {

    lateinit var moviewViewModel: WeatherViewModel

    var data: MutableLiveData<ArrayList<WeatherDetails>> = MutableLiveData()

    init {
        data.value = listWeather
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        data.value?.let {
            val weatherItemViewModel = WeatherItemViewModel(it.get(position))
            holder.bind(weatherItemViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemviewWeatherBinding =
            DataBindingUtil.inflate(layoutInflater, wipro.whetherfrecast.main.R.layout.itemview_weather, parent, false)
        DataBindingUtil.getDefaultComponent()
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int = data.value!!.size

    fun updateData(@Nullable data: ArrayList<WeatherDetails>?) {
        if (data == null || data.isEmpty()) {
            this.data.value.let {
                this.data.value = ArrayList()
            }
        } else {
            this.data.value = data
        }
        notifyDataSetChanged()
    }

    fun updateWeatherListItems(newWeatherList: List<WeatherDetails>) {
        val diffCallback = WeatherDiffUtilsCallBack(this.listWeather, newWeatherList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listWeather.clear()
        this.listWeather.addAll(newWeatherList)
        diffResult.dispatchUpdatesTo(this)
    }
}