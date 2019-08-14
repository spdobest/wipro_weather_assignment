package wipro.whetherfrecast.main.ui.base


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.include_error.*
import kotlinx.android.synthetic.main.include_progress.*
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.listeners.ToolbarListener

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    var mViewDataBinding: T? = null
    var mViewModel: V? = null

    var mLayoutId: Int = 0
    var mToolbarListener: ToolbarListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mToolbarListener = context as ToolbarListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context as Activity).localClassName + " must implement ToolbarListener ")
        }
    }

    protected fun setBaseEmptyView() {
        textViewErrorTitle.text = getString(R.string.error_nodata)
    }

    protected open fun showErrorBase(message: String) {
        relativeLayoutError.visibility = View.VISIBLE
        textViewErrorTitle.text = message
    }

    fun hideError() {
        relativeLayoutError?.visibility = View.GONE
    }

    fun hideProgressBar() {
        progressbarLoading?.visibility = View.GONE
    }

    fun showProgressBar() {
        progressbarLoading?.visibility = View.VISIBLE
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        inflater.inflate(mLayoutId, fragment_container)
        return mViewDataBinding?.root
    }

    protected fun setLayout(layoutId: Int) {
        mLayoutId = layoutId
    }

    override fun onDetach() {
        super.onDetach()
        mToolbarListener = null
    }

    abstract fun initialize()

    abstract fun setClickListener()

    /**
     * @return view model instance
     */
    abstract fun getViewModel(): V

    @LayoutRes
    abstract fun getLayoutId(): Int

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

}