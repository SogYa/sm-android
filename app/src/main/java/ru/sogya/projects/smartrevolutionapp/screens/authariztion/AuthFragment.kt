package ru.sogya.projects.smartrevolutionapp.screens.authariztion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sogya.data.utils.Constants
import com.sogya.data.utils.myCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentWebViewBinding

class AuthFragment : Fragment(R.layout.fragment_web_view) {
    private lateinit var binding: FragmentWebViewBinding
    lateinit var uri: String
    private val vm: AuthorizationVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonConnect.setOnClickListener {
            uri = binding.editTextUri.text.toString()
            if (uri.endsWith("/")) {
                uri = uri.substring(0, uri.length - 1)
                binding.editTextUri.setText(uri)

            }
            val redirectUri = "${uri.replace(" https ://", Constants.REDIRECT_URI)}/auth_callback"

            binding.logInWebView.visibility = VISIBLE
            binding.loginConstraint.visibility = GONE

            binding.logInWebView.settings.javaScriptEnabled = true
            binding.logInWebView.loadUrl(
                "$uri/auth/authorize?" +
                        "client_id=$uri" +
                        "&redirect_uri=${redirectUri}"
            )
            binding.logInWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.let {
                        if (request.url.toString().startsWith("${uri}/auth_callback")) {
                            request.url.getQueryParameter("code")?.let {
                                binding.loadingConstraint.visibility = VISIBLE
                                vm.getToken(uri, it, object : myCallBack<Boolean> {
                                    override fun data(t: Boolean) {
                                        Toast.makeText(
                                            context,
                                            getString(R.string.auth_succes),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                                    }

                                    override fun error() {
                                        Toast.makeText(
                                            context,
                                            getString(R.string.auth_failed),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })

                            } ?: kotlin.run {
                                Log.d("AUTHCODE", "Authorization code not received")
                            }
                        }
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
        }
    }

}