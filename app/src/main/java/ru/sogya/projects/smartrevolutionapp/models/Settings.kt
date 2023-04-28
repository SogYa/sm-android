package ru.sogya.projects.smartrevolutionapp.models

import ru.sogya.projects.smartrevolutionapp.R


data class Settings(
    val id: Int,
    val label: String,
    val desc: String,
    val navigationId: Int?,
    val resourceId:Int
) {
    companion object {
        const val APP_LOCKER = 0
        const val LOG_OUT = 1
        val settingsList = listOf(
            Settings(
                APP_LOCKER,
                "Пин-код",
                "Добавьте пинкод для доступа к серверу ",
                R.id.action_settingsFragment_to_appLockFragment,
                R.drawable.ic_baseline_lock_24
            ), Settings(
                LOG_OUT,
                "Выйти",
                "Отключиться от сервера",
                null,
                R.drawable.ic_baseline_exit_to_app_24
            )
        )
    }
}