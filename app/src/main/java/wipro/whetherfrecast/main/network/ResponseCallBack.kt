package wipro.whetherfrecast.main.network

public interface ResponseCallBack<S> {
    abstract fun onSuccess(value: S)
    abstract fun onError(error: String)
}