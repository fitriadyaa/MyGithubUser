package com.fitriadyaa.submission_githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitriadyaa.submission_githubuser.adapter.UserAdapter
import com.fitriadyaa.submission_githubuser.databinding.ActivityFavoriteBinding
import com.fitriadyaa.submission_githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

        favoriteViewModel.getFavoriteUsers().observe(this) { favoriteUsers ->
            adapter.setUserList(favoriteUsers)
            binding.favoriteProgressBar.visibility = View.GONE
        }


    }
}
