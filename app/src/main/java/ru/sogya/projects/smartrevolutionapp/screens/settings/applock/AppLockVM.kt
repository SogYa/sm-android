package ru.sogya.projects.smartrevolutionapp.screens.settings.applock

import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.sharedpreferences.GetBooleanPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App


class AppLockVM : ViewModel() {

    private var isLocked = false
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val updatePrefsUseCase = UpdatePrefsUseCase(sharedPreferencesRepository)
    private val getBooleanPrefsUseCase = GetBooleanPrefsUseCase(sharedPreferencesRepository)

    init {
        isLocked = getBooleanPrefsUseCase.invoke(Constants.PREFS_IS_LOCKED)
    }

    fun getLockedInfo(): Boolean {
        return isLocked
    }

    fun setPinCode(pinCode: String, pinVerify: String, myCallBack: MyCallBack<Boolean>) {
        if (pinCode == pinVerify) {
            updatePrefsUseCase.invoke(Constants.PREFS_IS_LOCKED, true)
            updatePrefsUseCase.invoke(Constants.PREFS_APPLOCK_PINCODE, pinVerify)
            myCallBack.data(true)
        } else {
            myCallBack.error()
        }
    }

    fun deactivateLocker() {
        updatePrefsUseCase.invoke(Constants.PREFS_IS_LOCKED, false)
    }
}
