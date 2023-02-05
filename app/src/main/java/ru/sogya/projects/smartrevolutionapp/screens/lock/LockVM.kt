package ru.sogya.projects.smartrevolutionapp.screens.lock

import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App


class LockVM : ViewModel() {
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    fun checkPin(pin: String, myCallBack: MyCallBack<Boolean>) {
        if (pin.length < 4 ||
            getStringPrefsUseCase.invoke(Constants.PREFS_APPLOCK_PINCODE) != pin
        ) {
            myCallBack.error()

        } else {
            myCallBack.data(true)
        }
    }
}