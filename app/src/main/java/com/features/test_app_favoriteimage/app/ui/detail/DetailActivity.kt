package com.features.test_app_favoriteimage.app.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.features.test_app_favoriteimage.R
import com.features.test_app_favoriteimage.app.ui.common.BaseActivity
import com.features.test_app_favoriteimage.db.models.DBImages
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity() {

    companion object{
        val EXTRA_IMAGE_ID = "image_id"

        fun newInstance(context: Context, id: Int) : Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_IMAGE_ID, id)
            }
        }
    }

    @Inject
    lateinit var factory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        appComponent.inject(this)

        this.viewModel = ViewModelProvider(viewModelStore, this.factory).get(DetailViewModel::class.java)



        this.viewModel.apply {
            image.observe(this@DetailActivity, Observer<DBImages> {
                updateDetails(it)
            })

            loadImage(getID())
        }

        iv_detail_favorite.setOnClickListener {
            viewModel.favoriteItem()
        }

    }

    private fun getID() : Int {
        return intent.extras?.getInt(EXTRA_IMAGE_ID) ?: -1
    }

    private fun updateDetails(dbImages: DBImages) {
        tv_detail_image_name.text = dbImages.imageName

        Glide.with(this)
            .load(dbImages.image)
            .centerCrop()
            .into(iv_detail_image)

        Glide.with(this)
            .load(
                if(dbImages.isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_favorite)
                else ContextCompat.getDrawable(this, R.drawable.ic_favorite_borde)
            ).centerCrop()
            .into(iv_detail_favorite)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        setResult(RESULT_OK, Intent())
        finish()
    }
}