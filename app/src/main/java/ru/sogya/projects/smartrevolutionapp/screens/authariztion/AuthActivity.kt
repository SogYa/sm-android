package ru.sogya.projects.smartrevolutionapp.screens.authariztion

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sogya.data.utils.myCallBack
import ru.sogya.projects.smartrevolutionapp.databinding.ActivityWebViewBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    lateinit var uri: String
    private val vm: AuthorizationVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConnect.setOnClickListener {
            uri = binding.editTextUri.text.toString()
            if (uri.endsWith("/")) {
                uri = uri.substring(0, uri.length - 1)
                binding.editTextUri.setText(uri)

            }

            binding.logInWebView.visibility = VISIBLE
            binding.loginConstraint.visibility = GONE

            binding.logInWebView.settings.javaScriptEnabled = true
            binding.logInWebView.loadUrl(
                "$uri/auth/authorize?" +
                        "client_id=$uri" +
                        "&redirect_uri=${uri}/auth_callback"
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
                                        Log.d("AUTHCODE", it)
                                        finish()
                                    }

                                    override fun error() {
                                        Toast.makeText(
                                            this@AuthActivity,
                                            "Authorization failed",
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