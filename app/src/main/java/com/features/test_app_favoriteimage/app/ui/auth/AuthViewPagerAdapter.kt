package com.features.test_app_favoriteimage.app.ui.auth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.features.test_app_favoriteimage.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.layout_auth.view.*

class AuthViewPagerAdapter(val listener: AuthListener) : RecyclerView.Adapter<AuthViewPagerAdapter.PagerViewHolder>() {

    private lateinit var context: Context
    private var arr = arrayOf(ScreenType.SCREEN_LOG_IN, ScreenType.SCREEN_SIGN_UP)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        this.context = parent.context
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_auth, parent, false))
    }

    override fun getItemCount(): Int = arr.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {

        val screenType: ScreenType? = when(position) {
            ScreenType.SCREEN_LOG_IN.value -> {
                holder.title.text = context.getString(R.string.log_in)
                holder.message.text = context.getString(R.string.log_in_message)
                holder.btn_auth.text = context.getString(R.string.log_in)
                ScreenType.SCREEN_LOG_IN
            }

            ScreenType.SCREEN_SIGN_UP.value -> {
                holder.title.text = context.getString(R.string.sign_up)
                holder.message.text = context.getString(R.string.sign_up_message)
                holder.btn_auth.text = context.getString(R.string.sign_up)
                ScreenType.SCREEN_SIGN_UP
            }

            else -> null
        }

        holder.btn_clear_email.setOnClickListener { holder.et_email.text?.clear() }
        holder.btn_clear_password.setOnClickListener { holder.et_password.text?.clear() }

        holder.btn_auth.setOnClickListener {
            listener.onAuthPressed(screenType, holder.et_email.toString(), holder.et_password.toString())
        }

    }

    interface AuthListener {
        fun onAuthPressed(type: ScreenType?, email: String?, pass: String?)
    }

    class PagerViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title : TextView = v.tv_auth_title
        val message : TextView = v.tv_auth_message
        val et_email : TextInputEditText = v.et_auth_email
        val et_password : TextInputEditText = v.et_auth_password
        val btn_clear_email : ImageView = v.iv_auth_clear_email
        val btn_clear_password : ImageView = v.iv_auth_clear_password
        val btn_auth : Button = v.btn_auth
    }
}

