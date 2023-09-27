package com.fitriadyaa.submission_githubuser.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitriadyaa.submission_githubuser.R
import com.fitriadyaa.submission_githubuser.adapter.SectionPagerAdapter
import com.fitriadyaa.submission_githubuser.databinding.ActivityDetailUserBinding
import com.fitriadyaa.submission_githubuser.repository.UserRepository
import com.fitriadyaa.submission_githubuser.viewmodel.DetailUserViewModel
import com.fitriadyaa.submission_githubuser.viewmodel.DetailUserViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var username: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME) ?: ""

        if (username.isEmpty()) {
            Toast.makeText(this, "Username not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupViewModel()
        setupUI()
        setupFavoriteButton()
    }

    private fun setupViewModel() {
        val viewModelFactory = DetailUserViewModelFactory(userRepository = UserRepository(application))
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailUserViewModel::class.java]

        viewModel.checkIsFavorite(username)

        viewModel.checkIsFavorite(username)

        viewModel.isFavorite.observe(this) { isFavorite ->
            val ivFavorite = findViewById<FloatingActionButton>(R.id.btnFavorite)
            if (isFavorite) {
                // User is a favorite, update the UI accordingly (e.g., change the icon to filled)
                ivFavorite.setImageResource(R.drawable.ic_favorite_24)
            } else {
                // User is not a favorite, update the UI accordingly (e.g., change the icon to outlined)
                ivFavorite.setImageResource(R.drawable.ic_favorite_border_24)
            }
        }
    }

    private fun setupUI() {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.getUserDetail().observe(this) { userDetail ->
                userDetail?.let {
                    // Use safe calls to access properties
                    binding.tvName.text = it.name ?: ""
                    binding.tvUsername.text = it.login ?: ""
                    binding.tvRepository.text = (it.publicRepos ?: 0).toString()
                    binding.tvBio.text = it.bio ?: ""
                    binding.tvFollower.text = (it.followers ?: 0).toString()
                    binding.tvFollowing.text = (it.following ?: 0).toString()
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


    private fun setupFavoriteButton() {
        val btnFavorite = findViewById<FloatingActionButton>(R.id.btnFavorite)
        btnFavorite.setOnClickListener {
            if (username.isNotEmpty()) {
                viewModel.toggleFavoriteStatus(username)
            }
        }
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

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
