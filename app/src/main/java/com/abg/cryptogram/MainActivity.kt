package com.abg.cryptogram

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class MainActivity : AppCompatActivity(), Navigator {
    private val quoteViewModel: QuoteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quoteViewModel.createListQuotes(resources.assets.open("test.csv"))
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, GameFragment()).commit()
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
            .replace(R.id.fragmentContainer, fragment).commit()
    }
}