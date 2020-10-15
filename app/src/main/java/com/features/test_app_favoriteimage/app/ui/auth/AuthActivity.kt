package com.features.test_app_favoriteimage.app.ui.auth

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.features.test_app_favoriteimage.R
import com.features.test_app_favoriteimage.app.ui.common.BaseActivity
import com.features.test_app_favoriteimage.app.ui.common.safeLet
import com.features.test_app_favoriteimage.app.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity() {

    @Inject
    lateinit var factory: AuthViewModelFactory
    private lateinit var viewModel: AuthViewModel
    private lateinit var adapter: AuthViewPagerAdapter

    private val ANIMATION_TIME: Long = 200L
    private var isMoving: Boolean = false
    private var screenType = ScreenType.SCREEN_LOG_IN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        appComponent.inject(this)
        this.viewModel = ViewModelProvider(viewModelStore, this.factory).get(AuthViewModel::class.java)

        initButton()

        this.adapter = AuthViewPagerAdapter(object : AuthViewPagerAdapter.AuthListener {
            override fun onLoginPressed(email: String?, pass: String?) {
                viewModel.login(email, pass)
            }

            override fun onSignupPressed(phone: String?, email: String?, pass: String?) {
                viewModel.signup(phone, email, pass)
            }
        })
        this.viewModel.apply {
            error.observe(this@AuthActivity, Observer<String> { error ->
                showToast(error)
            })
            successfulLogIn.observe(this@AuthActivity, Observer<Boolean> {
                startMainActivity()
            })
            successfulSignUp.observe(this@AuthActivity, Observer<Boolean> {
                startMainActivity()
            })
        }

        auth_view_pager.adapter = adapter
        auth_view_pager.isUserInputEnabled = false
        auth_view_pager.currentItem = ScreenType.SCREEN_LOG_IN.value

        tv_sign.setOnClickListener {
            if(!isMoving && screenType == ScreenType.SCREEN_LOG_IN) {
                // change to up
                showSignUpScreen()
            } else if(!isMoving && screenType == ScreenType.SCREEN_SIGN_UP){
                // change to in
                showSignInScreen()
            }
        }

    }

    private fun startMainActivity() {
        startActivity(Intent(this@AuthActivity, MainActivity::class.java))
        finish()
    }

    private fun initButton() {
        safeLet(tv_sign, view_right) { tvSign, viewRight ->
            tvSign.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    tvSign.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val oldParams = viewRight.layoutParams as RelativeLayout.LayoutParams
                    oldParams.width = tvSign.width
                    viewRight.layoutParams = oldParams

                    tvSign.x = viewRight.x

                    val signParams = tvSign.layoutParams as RelativeLayout.LayoutParams
                    signParams.addRule(RelativeLayout.ALIGN_PARENT_END)
                    tvSign.layoutParams = signParams

                    tvSign.text = getString(R.string.sign_up)
                }
            })
        }
    }

    private fun showSignUpScreen() {
        safeLet(auth_view_pager, tv_sign, view_left) { viewPager, tvSign, viewLeft ->

            animateSignButton(tvSign, tvSign.x, viewLeft.x, {
                isMoving = true
                viewPager.currentItem = ScreenType.SCREEN_SIGN_UP.value

            }, {
                isMoving = false
                tvSign.text = getString(R.string.log_in)
                screenType = ScreenType.SCREEN_SIGN_UP
            })

        }
    }

    private fun showSignInScreen() {
        safeLet(auth_view_pager, tv_sign, view_right) { viewPager, tvSign, viewRight ->

            animateSignButton(tvSign, tvSign.x, viewRight.x, {
                isMoving = true
                viewPager.currentItem = ScreenType.SCREEN_LOG_IN.value

            }, {
                isMoving = false
                tvSign.text = getString(R.string.sign_up)
                screenType = ScreenType.SCREEN_LOG_IN
            })


        }
    }

    private fun showToast(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun animateSignButton(view: View,
                                  currentX: Float,
                                  newX: Float,
                                  startAction: () -> Unit,
                                  endAction: () -> Unit) {

        val slideAnimator = ValueAnimator
            .ofFloat(currentX, newX)
            .setDuration(ANIMATION_TIME)

        slideAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            view.x = value
            view.requestLayout()
        }

        val animatorSet = AnimatorSet()
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(slideAnimator)
        animatorSet.doOnStart { startAction.invoke() }
        animatorSet.doOnEnd { endAction.invoke() }
        animatorSet.start()
    }



}