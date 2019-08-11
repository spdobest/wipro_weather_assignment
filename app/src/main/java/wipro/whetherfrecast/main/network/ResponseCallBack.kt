package wipro.whetherfrecast.main.network

interface ResponseCallBack<S> {
    fun onSuccess(value: S)
    fun onError(error: String)
}