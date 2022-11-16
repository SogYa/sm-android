package ru.sogya.projects.smartrevolutionapp.needtoremove

import android.content.Context
import android.content.SharedPreferences
import ru.sogya.projects.smartrevolutionapp.app.App


class SPControl {
    private val appPrefs: SharedPreferences
        get() = App.getApplicationContext()
            .getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE)

    //Метод для обновления значения в префах - Возвращает преф едитор
    private val prefsEditor: SharedPreferences.Editor
        get() = App.getApplicationContext()
            .getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE).edit()

    //метод обновления префов
    fun updatePrefs(key: String, valueStr: String) {
        prefsEditor.putString(key, valueStr).apply()
    }

    fun updatePrefs(key: String, valueBol: Boolean) {
        prefsEditor.putBoolean(key, valueBol).apply()
    }

    fun updatePrefs(key: String, valueFloat: Float) {
        prefsEditor.putFloat(key, valueFloat).apply()
    }

    fun updatePrefs(key: String, valueInt: Int) {
        prefsEditor.putInt(key, valueInt).apply()
    }

    fun updatePrefs(key: String, valueLong: Long) {
        prefsEditor.putLong(key, valueLong).apply()
    }

    fun updatePrefs(key: String, stringList: List<String>) {
        prefsEditor.putStringSet(key, HashSet(stringList)).apply()
    }

    fun updatePrefs(key: String, value: Double) {
        prefsEditor.putLong(key, java.lang.Double.doubleToRawLongBits(value)).apply()
    }

    //Получение объектов из префов
    fun getStringPrefs(strName: String): String {
        return appPrefs.getString(strName, " ").toString()
    }

    fun getBoolPrefs(strName: String): Boolean {
        return appPrefs.getBoolean(strName, false)
    }


    fun getIntPrefs(strName: String): Int {
        return appPrefs.getInt(strName, 0)
    }

    fun getLongPrefs(strName: String): Long {
        return appPrefs.getLong(strName, 0)
    }

//    fun getStringListPrefs(key: String): List<String> {
//        val stringSet: Set<String> = appPrefs.getStringSet(key, HashSet())
//        return stringSet.let { ArrayList(it) } ?: ArrayList()
//    }

    fun getDoublePrefs(key: String): Double {
        return java.lang.Double.longBitsToDouble(
            appPrefs.getLong(
                key,
                java.lang.Double.doubleToLongBits(0.0)
            )
        )
    }

    companion object {
        private const val APP_PREFS_NAME = "appPrefsName"

        //Класс который описывает управление префами - получить префы, обновить префы
        fun getIstance(): SPControl {
            return SPControl()
        }
    }
}