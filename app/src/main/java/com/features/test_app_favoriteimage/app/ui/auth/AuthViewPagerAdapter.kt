package com.features.test_app_favoriteimage.app.ui.auth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.features.test_app_favoriteimage.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.layout_log_in.view.*
import kotlinx.android.synthetic.main.layout_sign_up.view.*


class AuthViewPagerAdapter(val listener: AuthListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var arr = arrayOf(ScreenType.SCREEN_LOG_IN, ScreenType.SCREEN_SIGN_UP)

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            ScreenType.SCREEN_LOG_IN.value -> ScreenType.SCREEN_LOG_IN.value
            ScreenType.SCREEN_SIGN_UP.value -> ScreenType.SCREEN_SIGN_UP.value
            else -> ScreenType.SCREEN_LOG_IN.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return when(viewType) {
            ScreenType.SCREEN_LOG_IN.value -> LoginViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_log_in, parent, false))
            ScreenType.SCREEN_SIGN_UP.value -> SignupViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_sign_up, parent, false))
            else -> LoginViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_log_in, parent, false))
        }

    }

    override fun getItemCount(): Int = arr.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            ScreenType.SCREEN_LOG_IN.value -> {
                val loginHolder = holder as LoginViewHolder


                loginHolder.btn_log_in_clear_email.setOnClickListener { loginHolder.et_log_in_email.text?.clear() }
                loginHolder.btn_log_in_clear_password.setOnClickListener { loginHolder.et_log_in_password.text?.clear() }

                loginHolder.et_log_in_password.setOnEditorActionListener { _, i, _ ->
                    if(i == EditorInfo.IME_ACTION_DONE) {
                        listener.onLoginPressed(
                            loginHolder.et_log_in_email.text.toString(),
                            loginHolder.et_log_in_password.text.toString())
                    }
                    false
                }

                loginHolder.btn_log_in.setOnClickListener {
                    listener.onLoginPressed(
                        loginHolder.et_log_in_email.text.toString(),
                        loginHolder.et_log_in_password.text.toString())
                }
            }

            ScreenType.SCREEN_SIGN_UP.value -> {
                val signupHolder = holder as SignupViewHolder

                signupHolder.btn_sign_up_clear_phone.setOnClickListener { signupHolder.et_sign_up_phone.text?.clear() }
                signupHolder.btn_sign_up_clear_email.setOnClickListener { signupHolder.et_sign_up_email.text?.clear() }
                signupHolder.btn_sign_up_clear_password.setOnClickListener { signupHolder.et_sign_up_password.text?.clear() }

                signupHolder.et_sign_up_password.setOnEditorActionListener { _, i, _ ->
                    if(i == EditorInfo.IME_ACTION_DONE) {
                        listener.onSignupPressed(
                            signupHolder.et_sign_up_phone.text.toString(),
                            signupHolder.et_sign_up_email.text.toString(),
                            signupHolder.et_sign_up_password.text.toString())
                    }
                    false
                }

                signupHolder.btn_sign_up.setOnClickListener {
                    listener.onSignupPressed(
                        signupHolder.et_sign_up_phone.text.toString(),
                        signupHolder.et_sign_up_email.text.toString(),
                        signupHolder.et_sign_up_password.text.toString())
                }
            }
        }
    }

    interface AuthListener {
        fun onLoginPressed(email: String?, pass: String?)
        fun onSignupPressed(phone: String?, email: String?, pass: String?)
    }

    class SignupViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val sign_up_title : TextView = v.tv_sign_up_title
        val sign_up_message : TextView = v.tv_sign_up_message
        val et_sign_up_phone : TextInputEditText = v.et_sign_up_phone
        val et_sign_up_email : TextInputEditText = v.et_sign_up_email
        val et_sign_up_password : TextInputEditText = v.et_sign_up_password
        val btn_sign_up_clear_phone : ImageView = v.iv_sign_up_clear_phone
        val btn_sign_up_clear_email : ImageView = v.iv_sign_up_clear_email
        val btn_sign_up_clear_password : ImageView = v.iv_sign_up_clear_password
        val btn_sign_up : Button = v.btn_sign_up
    }

    class LoginViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val log_in_title : TextView = v.tv_log_in_title
        val log_in_message : TextView = v.tv_log_in_message
        val et_log_in_email : TextInputEditText = v.et_log_in_email
        val et_log_in_password : TextInputEditText = v.et_log_in_password
        val btn_log_in_clear_email : ImageView = v.iv_log_in_clear_email
        val btn_log_in_clear_password : ImageView = v.iv_log_in_clear_password
        val btn_log_in : Button = v.btn_log_in
    }
}

