package com.philosophyDictionary

import android.app.Activity
import android.content.Context
import androidx.annotation.StyleRes


class ThemeManager(private var activity: Activity) {
    private val NIGHT_MODE = "night_mode"

    private var mSharedPref =  activity.getPreferences(Context.MODE_PRIVATE)
    fun setAppTheme(@StyleRes style: Int) {
        activity.setTheme(style)
    }

    fun setupTheme(state: Boolean){
        if (!state) {
            setAppTheme(R.style.AppTheme_Night)
        } else {
            setAppTheme(R.style.AppTheme_Light)
        }
        setIsNightModeEnabled(!state)
        activity.recreate()
    }


    fun isNightModeEnabled(): Boolean {
        return mSharedPref!!.getBoolean(NIGHT_MODE, false)
    }

    private fun setIsNightModeEnabled(state: Boolean) {
        val mEditor = mSharedPref!!.edit()
        mEditor.putBoolean(NIGHT_MODE, state)
        mEditor.apply()
    }
}