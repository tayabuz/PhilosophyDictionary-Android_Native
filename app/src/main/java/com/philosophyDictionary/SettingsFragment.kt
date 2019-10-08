package com.philosophyDictionary

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat


class SettingsFragment : PreferenceFragmentCompat()  {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val themeSwitch: SwitchPreferenceCompat? = this.findPreference("switch_dark_theme")
        themeSwitch!!.setOnPreferenceChangeListener { preference, _ ->
            val switched = (preference as SwitchPreferenceCompat).isChecked
            val themeManager = ThemeManager(activity as Activity)
            themeManager.setupTheme(switched)

            true
        }

        val clearFavourites: Preference? = findPreference("clear_favourites")
        clearFavourites!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val theme = if (ThemeManager(activity!!).isNightModeEnabled()) R.style.AlertDialogNight else R.style.AlertDialogLight
            val alertDialog = AlertDialog.Builder(context!!, theme)
            alertDialog.setMessage(R.string.message_clear_favourites)
            alertDialog.setCancelable(true)
            alertDialog.setPositiveButton(R.string.confirm) { _, _ ->
                MainActivity.viewModel.deleteAll()
            }
            alertDialog.setNegativeButton(
                R.string.cancel
            ) { dialog, _ -> dialog.cancel() }
            alertDialog.show()

            false
        }
    }
}
