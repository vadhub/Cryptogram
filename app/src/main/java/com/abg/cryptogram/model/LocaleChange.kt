package com.abg.cryptogram.model

import android.content.Context
import android.content.res.Resources
import java.util.Locale


class LocaleChange {
    companion object {
        // Stack Overflow by Alex Volovoy change on Kotlin
        fun setLocale(context: Context, languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val resources: Resources = context.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        fun getLocale(context: Context): String {
            return context.resources.configuration.locale.language
        }
    }
}