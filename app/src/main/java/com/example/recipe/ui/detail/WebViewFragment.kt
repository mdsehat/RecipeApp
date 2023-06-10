package com.example.recipe.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipe.R
import com.example.recipe.databinding.FragmentStepsBinding
import com.example.recipe.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {
    //Binding
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    //Other
    private val args: WebViewFragmentArgs by navArgs()
    private var link = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWebViewBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Args
        args.let {
            link = it.link
        }
        //InitViews
        binding.apply {
            //Back
            iconBackWeb.setOnClickListener {
                findNavController().popBackStack(R.id.webViewFragment,true)
            }
            //WebView
            webView.apply {
                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    builtInZoomControls = false
                }
                webViewClient = WebViewClient()
                isHorizontalScrollBarEnabled = true
                isVerticalScrollBarEnabled = true
                webChromeClient = object : WebChromeClient(){
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        if (newProgress<100 && linearProgress.isGone){
                            linearProgress.visibility = View.VISIBLE
                        }
                        linearProgress.progress = newProgress
                        if (newProgress == 100) linearProgress.visibility = View.INVISIBLE
                    }
                }
                loadUrl(link)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}