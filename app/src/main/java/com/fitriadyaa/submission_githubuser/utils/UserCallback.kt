package com.fitriadyaa.submission_githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.fitriadyaa.submission_githubuser.model.UserModel

class UserCallback(
    private val oldList: List<UserModel>,
    private val newList: List<UserModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
