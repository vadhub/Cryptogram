package com.abg.cryptogram.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abg.cryptogram.Navigator
import com.abg.cryptogram.QuoteViewModel
import com.abg.cryptogram.R
import com.abg.cryptogram.data.SaveConfig
import java.util.Locale

class SettingsFragment : Fragment() {

    private lateinit var navigator: Navigator
    private lateinit var saveConfig: SaveConfig
    private val quoteViewModel: QuoteViewModel by activityViewModels()

    fun setSaveConfig(saveConfig: SaveConfig) {
        this.saveConfig = saveConfig
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as Navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val radioButtonRu = view.findViewById<RadioButton>(R.id.radioButtonRu)
        val radioButtonEn = view.findViewById<RadioButton>(R.id.radioButtonEn)

        if (saveConfig.getLanguage() == "ru") {
            radioButtonRu.isChecked = true
        } else {
            radioButtonEn.isChecked = true
        }

        radioButtonEn.setOnClickListener {
            saveConfig.changeLanguage(Locale.ENGLISH.language)
            quoteViewModel.createListQuotes(resources.assets.open("test_en.csv"))
        }

        radioButtonRu.setOnClickListener {
            saveConfig.changeLanguage("ru")
            quoteViewModel.createListQuotes(resources.assets.open("test.csv"))
        }

        view.findViewById<TextView>(R.id.ok_text).setOnClickListener { navigator.startFragment(GameFragment()) }
    }
}