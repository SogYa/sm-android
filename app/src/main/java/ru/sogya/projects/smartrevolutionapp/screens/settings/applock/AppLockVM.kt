package ru.sogya.projects.smartrevolutionapp.screens.settings.applock

import androidx.lifecycle.ViewModel
import com.sogya.data.utils.Constants
import com.sogya.data.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class AppLockVM : ViewModel() {

    private var isLocked = false

    init {
        isLocked = SPControl.getInstance().getBoolPrefs(Constants.PREFS_IS_LOCKED)
    }

    fun getLockedInfo(): Boolean {
        return isLocked
    }

    fun setPinCode(pinCode: String, pinVerify: String, myCallBack: MyCallBack<Boolean>) {
        if (pinCode == pinVerify) {
            SPControl.getInstance().updatePrefs(Constants.PREFS_IS_LOCKED, true)
            SPControl.getInstance().updatePrefs(Constants.PREFS_APPLOCK_PINCODE, pinVerify)
            myCallBack.data(true)
        } else {
            myCallBack.error()
        }
    }

    fun deactivateLocker() {
        SPControl.getInstance().updatePrefs(Constants.PREFS_IS_LOCKED, false)
    }
}
