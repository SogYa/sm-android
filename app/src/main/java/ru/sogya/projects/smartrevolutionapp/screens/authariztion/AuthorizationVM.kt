package ru.sogya.projects.smartrevolutionapp.screens.authariztion

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.data.utils.Constants
import com.sogya.data.utils.myCallBack
import com.sogya.domain.models.TokenInfo
import com.sogya.domain.usecases.GetTokenUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl


class AuthorizationVM : ViewModel() {
    private val repository = NetworkRepositoryImpl()
    private val getTokenUseCase = GetTokenUseCase(repository)


    fun getToken(baseUri: String, authCode: String, myCallBack: myCallBack<Boolean>) {
        getTokenUseCase.invoke(baseUri, authCode).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<TokenInfo>() {
                override fun onSuccess(t: TokenInfo) {
                    Log.d("TOKEN", t.access_token)
                    SPControl.getIstance().updatePrefs(Constants.URI, baseUri)
                    SPControl.getIstance().updatePrefs(Constants.AUTH_TOKEN, t.access_token)
                    myCallBack.data(true)
                }

                override fun onError(e: Throwable) {
                    Log.d("TOKEN_ERROR", e.message.toString())
                    myCallBack.error()
                }
            })
    }
}