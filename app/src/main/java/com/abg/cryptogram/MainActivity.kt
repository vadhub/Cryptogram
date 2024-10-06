package com.abg.cryptogram

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.ui.GameFragment
import com.abg.cryptogram.ui.tutorial.TutorialFragment
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.utils.pub.checkPurchasesAvailability
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult

class MainActivity : AppCompatActivity(), Navigator {
    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null
    private val idAd = "R-M-11000227-1" // demo-interstitial-yandex
    private val quoteViewModel: QuoteViewModel by viewModels()

    val billingClient: RuStoreBillingClient = RuStoreBillingClientFactory.create(
        context = applicationContext,
        consoleApplicationId = "2063565921",
        deeplinkScheme = "com.abg.cryptogram.scheme",
        // Опциональные параметры
        themeProvider = null,
        debugLogs  = false,
        externalPaymentLoggerFactory = null,
    )

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        billingClient.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            billingClient.onNewIntent(intent)
        }

        interstitialAdLoader = InterstitialAdLoader(this).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    this@MainActivity.interstitialAd = interstitialAd
                    // The ad was loaded successfully. Now you can show loaded ad.
                }

                override fun onAdFailedToLoad(error: AdRequestError) {
                    // Ad failed to load with AdRequestError.
                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                }
            })
        }

        val saveConfig = SaveConfig(this)
        val fileName: String = if (saveConfig.getLanguage() == "ru") "test.csv" else "test_en.csv"
        quoteViewModel.createListQuotes(resources.assets.open(fileName))

        if (saveConfig.getIsTutorComplete()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GameFragment()).commit()
        } else {
            startFragment(TutorialFragment())
        }
    }

    override fun loadInterstitialAd() {
        val adRequestConfiguration = AdRequestConfiguration.Builder(idAd).build()
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
            .replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun showAd() {
        interstitialAd?.apply {
            setAdEventListener(object : InterstitialAdEventListener {
                override fun onAdShown() {
                    // Called when ad is shown.
                }
                override fun onAdFailedToShow(adError: AdError) {
                    // Called when an InterstitialAd failed to show.
                    // Clean resources after Ad dismissed
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null

                    // Now you can preload the next interstitial ad.
                    loadInterstitialAd()
                }
                override fun onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null

                    // Now you can preload the next interstitial ad.
                    loadInterstitialAd()
                }
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                }
                override fun onAdImpression(impressionData: ImpressionData?) {
                    // Called when an impression is recorded for an ad.
                }
            })
            show(this@MainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        destroyInterstitialAd()
    }

    override fun destroyInterstitialAd() {
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }
}