package ru.sogya.projects.smartrevolutionapp.screens.autharization

import android.annotation.SuppressLint
import android.graphics.Bitmap
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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentWebViewBinding

class AuthFragment : Fragment(R.layout.fragment_web_view) {
    private lateinit var binding: FragmentWebViewBinding
    private lateinit var serverUri: String
    private lateinit var serverName: String
    private val vm: AuthorizationVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vm.getLoadingLiveData().observe(viewLifecycleOwner) {
            binding.loadingConstraint.visibility = it
        }
        vm.getNavigationLiveData().observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    R.id.action_authFragment_to_groupFragment,
                    bundleOf(),
                    navOptions {
                        launchSingleTop = true
                        popUpTo(R.id.nav_graph) {
                            inclusive = true
                        }
                    })
                vm.closeWebSocket()
            }
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonConnect.setOnClickListener {
            serverUri = binding.editTextServerUri.text.toString()
            serverName = binding.editTextServerName.text.toString()
            if (serverUri.endsWith("/")) {
                serverUri = serverUri.substring(0, serverUri.length - 1)
                binding.editTextServerUri.setText(serverUri)
            }
            val redirectUri =
                "${serverUri.replace(" https ://", Constants.REDIRECT_URI)}/auth_callback"
            binding.logInWebView.settings.javaScriptEnabled = true
            if (serverUri != "" && serverName != "") {
                binding.loginConstraint.visibility = GONE
                binding.logInWebView.loadUrl(
                    "$serverUri/auth/authorize?" + "client_id=$serverUri" + "&redirect_uri=${redirectUri}"
                )
            } else {
                Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show()
            }
            binding.logInWebView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.loadingConstraint.visibility = VISIBLE
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.loadingConstraint.visibility = GONE
                    binding.webViewConstarint.visibility = VISIBLE
                    binding.logInWebView.loadUrl(
                        "javascript:(function() { " + "document.getElementsByTagName('div')[1].style.visibility = 'hidden';  " + "})()"
                    )
                }
                override fun shouldOverrideUrlLoading(
                    view: WebView?, request: WebResourceRequest?
                ): Boolean {
                    request?.let {
                        if (request.url.toString().startsWith("${serverUri}/auth_callback")) {
                            request.url.getQueryParameter("code")?.let {
                                vm.getToken(
                                    serverName,
                                    serverUri,
                                    it,
                                    object : MyCallBack<Boolean> {
                                        override fun data(t: Boolean) {
                                            Toast.makeText(
                                                context,
                                                getString(R.string.auth_succes),
                                                Toast.LENGTH_SHORT
                                            ).show()
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