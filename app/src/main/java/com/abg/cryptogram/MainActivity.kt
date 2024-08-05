package com.abg.cryptogram

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.ui.GameFragment
import com.abg.cryptogram.ui.tutorial.TutorialFragment

class MainActivity : AppCompatActivity(), Navigator {
    private val quoteViewModel: QuoteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName: String = if (resources.configuration.locale.language == "ru") {
            "test.csv"
        } else {
            "test_en.csv"
        }
        quoteViewModel.createListQuotes(resources.assets.open(fileName))
        val saveConfig = SaveConfig(this)
        if (saveConfig.getIsTutorComplete()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GameFragment()).commit()
        } else {
            startFragment(TutorialFragment())
        }
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
            .replace(R.id.fragmentContainer, fragment).commit()
    }
}