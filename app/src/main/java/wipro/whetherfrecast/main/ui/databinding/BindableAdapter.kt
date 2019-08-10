package wipro.whetherfrecast.main.ui.databinding

interface BindableAdapter<T> {
    fun setData(items: List<T>)
    fun changedPositions(positions: Set<Int>)
}