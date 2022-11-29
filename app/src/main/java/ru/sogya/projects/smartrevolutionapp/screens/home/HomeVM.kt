package ru.sogya.projects.smartrevolutionapp.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.data.utils.Constants
import com.sogya.domain.models.Message
import com.sogya.domain.usecases.GetMessageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class HomeVM : ViewModel() {
    val messageLiveData = MutableLiveData<String>()
    private val repository = NetworkRepositoryImpl()
    private val getMessageUseCase = GetMessageUseCase(repository)

    init {
        getMessageUseCase.invoke(
            SPControl.getIstance().getStringPrefs(Constants.URI),
            "Bearer ${SPControl.getIstance().getStringPrefs(Constants.AUTH_TOKEN)}"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Message>() {
                override fun onSuccess(t: Message) {
                    messageLiveData.value = t.message

                }

                override fun onError(e: Throwable) {
                    Log.d("HOME_ERROR", e.message.toString())
                }

            })
    }
}