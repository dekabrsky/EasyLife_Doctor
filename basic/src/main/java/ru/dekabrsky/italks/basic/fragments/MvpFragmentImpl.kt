package ru.dekabrsky.italks.basic.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import moxy.MvpDelegate
import ru.dekabrsky.italks.basic.logging.log

open class MvpFragmentImpl : Fragment() {
    private var stateSaved: Boolean = false
    protected val mvpDelegate: MvpDelegate<out MvpFragmentImpl> by lazy { MvpDelegate(this) }

    fun onCreateMvp(savedInstanceState: Bundle?, dispatchToMvpDelegate: Boolean) {
        super.onCreate(savedInstanceState)
        if (dispatchToMvpDelegate) {
            mvpDelegate.onCreate(savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        stateSaved = false
        try {
            mvpDelegate.onAttach()
        } catch (e: Exception) {
            log(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun onResume() {
        super.onResume()
        stateSaved = false
        try {
            mvpDelegate.onAttach()
        } catch (e: Exception) {
            log(e)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateSaved = true
        try {
            mvpDelegate.onSaveInstanceState(outState)
            mvpDelegate.onDetach()
        } catch (e: Exception) {
            log(e)
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            mvpDelegate.onDetach()
        } catch (e: Exception) {
            log(e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            mvpDelegate.onDetach()
            mvpDelegate.onDestroyView()
        } catch (e: Exception) {
            log(e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (requireActivity().isFinishing) {
                mvpDelegate.onDestroy()
                onFinallyFinished()
                return
            }

            if (stateSaved) {
                stateSaved = false
                return
            }

            var anyParentIsRemoving = false
            var parent = parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }

            if (isRemoving || anyParentIsRemoving) {
                mvpDelegate.onDestroy()
                onFinallyFinished()
            }
        } catch (e: Exception) {
            log(e)
        }
    }

    protected open fun onFinallyFinished() {}
}