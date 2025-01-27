package ru.dekabrsky.easylife.basic.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.dekabrsky.easylife.basic.R
import ru.dekabrsky.easylife.basic.logging.log


abstract class BasicFragment : MvpFragmentImpl(), BasicView {
    protected abstract val layoutRes: Int
    open val scopeName: String = this.javaClass.name

    private val lifeCycleDisposable by lazy { CompositeDisposable() }

    private val isInjectionEnabled by lazy { true }

    protected val compatActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity

    protected val actionBar: ActionBar?
        get() = compatActivity?.supportActionBar

    protected open val toolbarId: Int = R.id.toolbar
    protected val toolbar: Toolbar?
        get() = if (toolbarId != 0) view?.findViewById(toolbarId) else null

    private var loadingDialog: Dialog? = null

    @Suppress("TooGenericExceptionCaught")
    @SuppressLint("MissingSuperCall")
    final override fun onCreate(savedInstanceState: Bundle?) {
        try {
            setHasOptionsMenu(true)

            if (isInjectionEnabled) {
                injectDependencies()
            }

            onCreateMvp(savedInstanceState, dispatchToMvpDelegate = isInjectionEnabled)
            onSecureCreate(savedInstanceState)
        } catch (e: Exception) {
            onCreateMvp(savedInstanceState, dispatchToMvpDelegate = false)
            log(e, "проблема при попытке инъекции")
        }
    }

    @CallSuper
    protected open fun onSecureCreate(savedInstanceState: Bundle?) {
    }

    protected open fun injectDependencies() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return onSaveCreateView(inflater, container, savedInstanceState)
    }

    protected open fun onSaveCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.invalidateOptionsMenu()
        compatActivity?.setSupportActionBar(toolbar)
    }

    protected fun Disposable.addLifeCycle(): Disposable {
        lifeCycleDisposable.add(this)
        return this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifeCycleDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCycleDisposable.dispose()
    }

    protected fun invalidateOptionsMenu() = compatActivity?.invalidateOptionsMenu()

    fun hideKeyboard() {
        activity?.let { activity ->
            val view = activity.currentFocus ?: view ?: return

            val inputManager = activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
    }

    override fun showError(error: Throwable, action: (() -> Unit)?) {
        val snackBar = Snackbar
            .make(requireView(), error.localizedMessage ?: "", Snackbar.LENGTH_LONG)

//        action.let {
//            snackBar.setAction(R.string.retry, View.OnClickListener { action })
//        }
        snackBar.show()

        Log.d(scopeName, error.stackTraceToString())
    }

    override fun showError(error: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.error))
                .setMessage(error)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show()
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun setLoadingVisibility(isVisible: Boolean) {
        if (isVisible) {
            if (loadingDialog == null) {
                loadingDialog =
                    AlertDialog.Builder(requireActivity())
                        .setMessage(getString(R.string.loading))
                        .setCancelable(false)
                        .create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    abstract fun onBackPressed()

    protected open fun errorCloseCallback() {}
}