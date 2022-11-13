package ru.sogya.projects.smartrevolutionapp.screens.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.domain.models.Message
import com.sogya.domain.usecases.GetMessageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AuthorizationVM : ViewModel() {
    private val repository = NetworkRepositoryImpl()
    private val getMessageUseCase = GetMessageUseCase(repository)

    fun getMessage(baseUri: String, token: String) {
        getMessageUseCase.invoke(baseUri, "Bearer $token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Message>() {
                override fun onSuccess(t: Message) {
                    Log.d("TestSuc", t.message)
                }

                override fun onError(e: Throwable) {
                    Log.d("TestSuc", e.message.toString())
                }

            })
    }
}