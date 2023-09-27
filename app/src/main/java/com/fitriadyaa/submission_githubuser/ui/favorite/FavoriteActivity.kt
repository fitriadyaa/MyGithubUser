package com.fitriadyaa.submission_githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitriadyaa.submission_githubuser.adapter.UserAdapter
import com.fitriadyaa.submission_githubuser.databinding.ActivityFavoriteBinding
import com.fitriadyaa.submission_githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize your ViewModel
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter
    }
}
