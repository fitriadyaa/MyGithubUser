package com.fitriadyaa.submission_githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitriadyaa.submission_githubuser.adapter.FavoriteAdapter
import com.fitriadyaa.submission_githubuser.data.local.entity.UserEntity
import com.fitriadyaa.submission_githubuser.databinding.ActivityFavoriteBinding
import com.fitriadyaa.submission_githubuser.ui.detail.DetailUserActivity
import com.fitriadyaa.submission_githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        adapter = FavoriteAdapter(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserEntity) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.username)
                intent.putExtra(DetailUserActivity.EXTRA_ID, user.id)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
                startActivity(intent)
            }
        })

        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.adapter = adapter

        favoriteViewModel.getFavoriteUsers().observe(this) { users ->
            if (!users.isNullOrEmpty()) {
                binding?.rvUser?.visibility = View.VISIBLE
                adapter.submitList(users)
            } else {
                binding?.rvUser?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showDataFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showDataFavorite() {
        val rvUser = binding?.rvUser
        val tvNoData = binding?.tvNoData

        favoriteViewModel.getFavoriteUsers().observe(this) { users ->
            if (users.isNullOrEmpty()) {
                tvNoData?.visibility = View.VISIBLE
                rvUser?.visibility = View.INVISIBLE
            } else {
                rvUser?.visibility = View.VISIBLE
                adapter.submitList(users)
                tvNoData?.visibility = View.GONE
            }
        }
    }

}
