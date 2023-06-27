package ru.sogya.projects.smartrevolutionapp.screens.lock

import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LockVM @Inject constructor(
    private val getStringPrefsUseCase: GetStringPrefsUseCase
) : ViewModel() {

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