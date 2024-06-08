package ru.dekabrsky.webview.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dekabrsky.webview.R
import ru.dekabrsky.webview.databinding.FragmentWebViewBinding
import ru.dekabrsky.webview.presentation.model.WebViewArgs

class WebViewFragment: Fragment() {

    private var args: WebViewArgs? = null

    private var binding: FragmentWebViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentWebViewBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args?.link?.let { binding?.webView?.loadUrl(it) }

        binding?.toolbar?.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding?.toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
    }
    companion object {
        fun newInstance(args: WebViewArgs) =
            WebViewFragment().apply { this.args = args }
    }
}