package com.fitriadyaa.submission_githubuser.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitriadyaa.submission_githubuser.R
import com.fitriadyaa.submission_githubuser.adapter.SectionPagerAdapter
import com.fitriadyaa.submission_githubuser.data.local.entity.UserEntity
import com.fitriadyaa.submission_githubuser.data.local.room.UserDatabase
import com.fitriadyaa.submission_githubuser.databinding.ActivityDetailUserBinding
import com.fitriadyaa.submission_githubuser.viewmodel.DetailUserViewModel
import com.fitriadyaa.submission_githubuser.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()
    private lateinit var username: String
    private lateinit var avatar: String
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        val id = intent.getIntExtra(EXTRA_ID, 0)
        avatar = intent.getStringExtra(EXTRA_AVATAR) ?: ""

        if (username.isEmpty()) {
            Toast.makeText(this, "Username not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupUI()

        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteViewModel.check(id)
            withContext(Dispatchers.Main) {

                if (count > 0) {
                    favoriteIcon(true)

                } else {
                    favoriteIcon(false)
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val count = favoriteViewModel.check(id)
                withContext(Dispatchers.Main) {

                    if (count > 0) {
                        favoriteIcon(false)
                        favoriteViewModel.deleteFavorite(id)
                    } else {
                        favoriteIcon(true)
                        favoriteViewModel.saveFavorite(username, avatar, id)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)
        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.getUserDetail().observe(this) {
                if (it != null) {
                    binding.tvName.text = it.name
                    binding.tvUsername.text = it.login
                    binding.tvRepository.text = it.publicRepos.toString()
                    binding.tvBio.text = it.bio
                    binding.tvFollower.text = it.followers.toString()
                    binding.tvFollowing.text = it.following.toString()
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(binding.ivAvatar)
                    showLoading(false)
                }
            }
        } else {
            Toast.makeText(this, "Username not found.", Toast.LENGTH_SHORT).show()
            finish()
        }


        val sectionPagerAdapter = SectionPagerAdapter(this, this, binding.tabs, binding.viewPager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        sectionPagerAdapter.setupTabs()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun favoriteIcon(isFavorite: Boolean) {
        binding.btnFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                if (isFavorite) {
                    R.drawable.ic_favorite_24
                } else {
                    R.drawable.ic_favorite_border_24
                }
            )
        )
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
