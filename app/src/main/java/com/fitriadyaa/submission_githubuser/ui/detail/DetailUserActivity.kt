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
import com.fitriadyaa.submission_githubuser.adapter.SectionPagerAdapter
import com.fitriadyaa.submission_githubuser.databinding.ActivityDetailUserBinding


class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

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
                // Handle back button click here
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