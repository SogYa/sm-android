package ru.sogya.projects.smartrevolutionapp.models

import ru.sogya.projects.smartrevolutionapp.R


data class Settings(
    val id: Int,
    val label: String,
    val icon: Int
) {
    companion object {
        const val APP_LOCKER = 0
        val settingsList = listOf<Settings>(
            Settings(
                APP_LOCKER, "Pin-code", R.drawable.ic_baseline_lock_24
            )
        )
    }
}