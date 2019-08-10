package wipro.whetherfrecast.main.ui.adapter.viewholders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import wipro.whetherfrecast.main.BR
import wipro.whetherfrecast.main.ui.viewmodel.WeatherItemViewModel


class WeatherViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherItemViewModel) {
        // binding.setVariable(BR.weatherViewModel, data)
        binding.setVariable(BR.weatherItemViewModel, weather)
        binding.executePendingBindings()
    }
}