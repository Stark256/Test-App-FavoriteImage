package com.features.test_app_favoriteimage.app.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.features.test_app_favoriteimage.R
import com.features.test_app_favoriteimage.app.ui.auth.AuthActivity
import com.features.test_app_favoriteimage.app.ui.common.BaseActivity
import com.features.test_app_favoriteimage.app.ui.detail.DetailActivity
import com.features.test_app_favoriteimage.db.models.DBImages
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)
        this.viewModel = ViewModelProvider(viewModelStore, this.factory).get(MainViewModel::class.java)

        this.adapter = MainAdapter(object : MainAdapter.ImageClickListener {
            override fun onFavoritePressed(dbImages: DBImages) {
                viewModel.favoriteItem(dbImages)
            }

            override fun onItemPressed(dbImages: DBImages) {
                startActivityForResult(DetailActivity.newInstance(this@MainActivity, dbImages.id), 25)
            }
        })
        rv_main.adapter = adapter
        rv_main.layoutManager = LinearLayoutManager(this)

        tiet_main_search.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSoftKeyboard(this, tiet_main_search)
                viewModel.searchImage(tiet_main_search.text.toString()) {
                    showErrorDialog()
                }
            }
            return@setOnEditorActionListener false
        }

        btn_log_out.setOnClickListener {
            this.viewModel.logOut {
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
            }
        }

        this.viewModel.apply {

            user.observe(this@MainActivity, Observer<String> {
                tv_user_email.text = it
            })

            addNewImage.observe(this@MainActivity, Observer<DBImages>{
                adapter.addSingleObject(it)
            })

            images.observe(this@MainActivity, Observer<ArrayList<DBImages>> {
                adapter.replaceAll(it)
            })

            loadImage.observe(this@MainActivity, Observer<String> {
                imageLink ->
                loadImage(imageLink){ bytes ->
                 viewModel.saveImage(bytes)
                }
            })

            isLoading.observe(this@MainActivity, Observer<Boolean> {
                pb_main.visibility = if(it) View.VISIBLE else View.GONE
            })

            loadActiveUser()
            loadAllImages()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 25) {
            viewModel.loadAllImages()
        }
    }

    private fun loadImage(imageLink: String, byteArray: (ByteArray) -> Unit) {
        Glide.with(this)
            .asBitmap()
            .load(imageLink)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: Target<Bitmap>?, isFirstResource: Boolean): Boolean { return false }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        val baos = ByteArrayOutputStream()
                        it.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        byteArray.invoke(baos.toByteArray())
                    }
                    return false
                }
            }).submit()//into(ImageView(this))
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setPositiveButton("OK") {dialog, i ->
                dialog.dismiss()
            }.setMessage("Cannot find image")
            .create().show()
    }

}