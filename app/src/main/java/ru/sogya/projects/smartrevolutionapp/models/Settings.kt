package ru.sogya.projects.smartrevolutionapp.models

import ru.sogya.projects.smartrevolutionapp.R


data class Settings(
    val id: Int,
    val label: String,
    val desc: String,
    val navigationId: Int?
) {
    companion object {
        const val APP_LOCKER = 0
        const val LOG_OUT = 1
        val settingsList = listOf(
            Settings(
                APP_LOCKER,
                "Pin-code",
                "Application lock management using pin-code",
                R.id.action_settingsFragment_to_appLockFragment
            ), Settings(
                LOG_OUT,
                "Log out",
                "Disconnecting from this server",
                null
            )
        )
    }
}