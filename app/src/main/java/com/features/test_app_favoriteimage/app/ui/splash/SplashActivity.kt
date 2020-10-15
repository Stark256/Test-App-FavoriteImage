package com.features.test_app_favoriteimage.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.features.test_app_favoriteimage.app.ui.auth.AuthActivity
import com.features.test_app_favoriteimage.app.ui.common.BaseActivity
import com.features.test_app_favoriteimage.app.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    companion object {
        private const val SPLASH_TIME = 200L
    }

    @Inject
    lateinit var factory: SplashViewModelFactory
    private lateinit var viewModel: SplashViewModel

    private val handler = Handler()
    private val splashRunnable = Runnable {
        if(isActiveUserExist) startMainActivity()
        else startAuthActivity()
    }
    private var isActiveUserExist: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        this.viewModel = ViewModelProvider(viewModelStore, this.factory).get(SplashViewModel::class.java)

        this.viewModel.apply {
            isActiveUserExist.observe(this@SplashActivity, Observer<Boolean> {
                this@SplashActivity.isActiveUserExist = it
                performDelayedStartActivity()
            })
            loadActiveUser()
        }

    }

    private fun performDelayedStartActivity() {
        handler.postDelayed(splashRunnable, SPLASH_TIME)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun startAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(splashRunnable)
    }
}