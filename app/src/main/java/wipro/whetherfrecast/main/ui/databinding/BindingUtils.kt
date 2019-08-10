package wipro.whetherfrecast.main.ui.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import wipro.whetherfrecast.main.ui.adapter.WeatherAdapter
import wipro.whetherfrecast.main.ui.model.WeatherDetails

@BindingAdapter("adapterData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<T>) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).setData(items)
    }
}


@BindingAdapter("changedPositions")
fun <T> setDataChanged(recyclerView: RecyclerView, positions: Set<Int>) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).changedPositions(positions)
    }

    @BindingAdapter("app:adapter")
    fun bind(recyclerView: RecyclerView, adapter: WeatherAdapter, data: ArrayList<WeatherDetails>) {
        recyclerView.setAdapter(adapter)
        adapter.updateData(data)
    }
}
