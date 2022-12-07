package ru.sogya.projects.smartrevolutionapp.models

import ru.sogya.projects.smartrevolutionapp.R


data class Settings(
    val id: Int,
    val label: String,
    val icon: Int,
    val navigationId: Int?
) {
    companion object {
        const val APP_LOCKER = 0
        const val LOG_OUT = 1
        val settingsList = listOf(
            Settings(
                APP_LOCKER,
                "Pin-code",
                R.drawable.ic_baseline_lock_24,
                R.id.action_settingsFragment_to_appLockFragment
            ), Settings(
                LOG_OUT,
                "Log out",
                R.drawable.ic_baseline_exit_to_app_24,
                null
            )
        )
    }
}