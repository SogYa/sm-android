package ru.sogya.projects.smartrevolutionapp.screens.lock

import androidx.lifecycle.ViewModel
import com.sogya.data.utils.Constants
import com.sogya.data.utils.myCallBack
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class LockVM : ViewModel() {
    fun checkPin(pin: String, myCallBack: myCallBack<Boolean>) {
        if (pin.length < 4 ||
            SPControl.getInstance().getStringPrefs(Constants.PREFS_APPLOCK_PINCODE) != pin
        ) {
            myCallBack.error()

        } else {
            myCallBack.data(true)
        }
    }
}