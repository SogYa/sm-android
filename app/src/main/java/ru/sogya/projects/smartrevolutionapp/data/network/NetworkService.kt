package ru.sogya.projects.smartrevolutionapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.sogya.projects.smartrevolutionapp.BuildConfig
import ru.sogya.projects.smartrevolutionapp.data.network.api.HomeAssistantApi
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkService {

    private fun getOkHttpClientInstance(readTimeoutInSec: Int): OkHttpClient? {

        return getOkHttpClientInstance(
            0,
            30
        )
    }

    private fun getOkHttpClientInstance(
        connectTimeoutInSec: Int,
        readTimeoutInSec: Int
    ): OkHttpClient? {
        try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
                .connectTimeout(connectTimeoutInSec.toLong(), TimeUnit.SECONDS)
                .readTimeout(readTimeoutInSec.toLong(), TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { hostname, session -> true }
            if (BuildConfig.BUILD_TYPE != "release") {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    fun getretrofitService(baseUri: String): HomeAssistantApi {


        return Retrofit.Builder()
            .baseUrl(baseUri)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClientInstance(30))
            .build()
            .create(HomeAssistantApi::class.java)
    }
}