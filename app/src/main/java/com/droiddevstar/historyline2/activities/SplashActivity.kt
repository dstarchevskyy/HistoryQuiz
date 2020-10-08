package com.droiddevstar.historyline2.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.droiddevstar.historyline2.databinding.ActivitySplashBinding
import com.droiddevstar.historyline2.startActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentForm;
import com.explorestack.consent.ConsentFormListener;
import com.explorestack.consent.ConsentInfoUpdateListener;
import com.explorestack.consent.ConsentManager;
import com.explorestack.consent.exception.ConsentManagerException;

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    private var consentForm: ConsentForm? = null

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val prefMusic = sharedPreferences.getBoolean("music", true)
        Timber.e("!!!prefMusic: $prefMusic")

        val prefLang = sharedPreferences.getString("language", "")
        Timber.e("!!!prefLang: $prefLang")

        setContentView(binding.rootView)
        resolveUserConsent();

//        @Suppress("DeferredResultUnused")
//        GlobalScope.async {
//            delay(2000)
//            if (GdprManager.wasConsentShowing(this@SplashActivity)) {
//                startActivity<LandingActivity>()
//            } else {
//                startActivity<GDPRActivity>()
//            }
//
//            finish()
//        }
    }

    private fun resolveUserConsent() {
        // Note: YOU MUST SPECIFY YOUR APPODEAL SDK KET HERE
        val consentManager: ConsentManager? = ConsentManager.getInstance(this)
        // Requesting Consent info update
        // Requesting Consent info update
        consentManager?.requestConsentInfoUpdate(
            APPODEAL_KEY,
            object : ConsentInfoUpdateListener {
                @Override
                open override fun onConsentInfoUpdated(consent: Consent?): Unit {
                    val consentShouldShow: Consent.ShouldShow? =
                        consentManager.shouldShowConsentDialog()
                    // If ConsentManager return Consent.ShouldShow.TRUE, than we should show consent form
                    if (consentShouldShow === Consent.ShouldShow.TRUE) {
                        showConsentForm()
                    } else {
                        // Start our main activity with default Consent value
                        startActivity<LandingActivity>()
                    }
                }

                @Override
                open override fun onFailedToUpdateConsentInfo(e: ConsentManagerException?): Unit {
                    // Start our main activity with default Consent value
                    startActivity<LandingActivity>()
                }
            })
    }

    // Displaying ConsentManger Consent request form
    private fun showConsentForm(): kotlin.Unit {
        if (consentForm == null) {
            consentForm = ConsentForm.Builder(this)
                .withListener(object : ConsentFormListener {
                    open override fun onConsentFormLoaded(): kotlin.Unit {
                        // Show ConsentManager Consent request form
                        consentForm?.showAsActivity()
                    }

                    open override fun onConsentFormError(error: ConsentManagerException?): kotlin.Unit {
                        Timber.e("Consent form error: " + error?.getReason())
                        // Start our main activity with default Consent value
                        startActivity<LandingActivity>()
                    }

                    open override fun onConsentFormOpened(): kotlin.Unit {
                        //ignore
                    }

                    open override fun onConsentFormClosed(consent: Consent?): kotlin.Unit {
                        val hasConsent: kotlin.Boolean =
                            consent?.getStatus() === Consent.Status.PERSONALIZED &&
                                    consent.getStatus() !== Consent.Status.NON_PERSONALIZED
                        // Start our main activity with resolved Consent value
                        startActivity<LandingActivity>() //TODO: pass consent valuee
                    }
                }).build()
        }
        // If Consent request form is already loaded, then we can display it, otherwise, we should load it first
        if (true == consentForm?.isLoaded()) {
            consentForm?.showAsActivity()
        } else {
            consentForm?.load()
        }
    }

}
