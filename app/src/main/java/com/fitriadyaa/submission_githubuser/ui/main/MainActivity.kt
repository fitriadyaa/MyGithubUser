package com.fitriadyaa.submission_githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitriadyaa.submission_githubuser.adapter.UserAdapter
import com.fitriadyaa.submission_githubuser.databinding.ActivityMainBinding
import com.fitriadyaa.submission_githubuser.model.UserModel
import com.fitriadyaa.submission_githubuser.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        binding.btnSearch.setOnClickListener{
            showLoading(true)
            searchUser()
        }

        binding.etQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                showLoading(true)
                searchUser()
                true
            } else {
                false
            }
        }


        viewModel.getSearchUser().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }

        showLoading(true)
        viewModel.setSearchUser("ahmad")
    }

    private fun searchUser() {
        val query = binding.etQuery.text.toString()
        if (query.isEmpty()) {
            showLoading(false)
            return
        }
        viewModel.setSearchUser(query)
    }

    private fun showLoading(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}