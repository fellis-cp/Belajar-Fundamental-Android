package com.dicoding.githubhanif.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import com.dicoding.githubhanif.databinding.FragmentFollowBinding
import com.dicoding.githubhanif.ui.detail.DetailViewModel
import com.dicoding.githubhanif.ui.main.Result
import com.dicoding.githubhanif.ui.main.UserAdapter

class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy { UserAdapter {  } }
    private val viewModel by activityViewModels<DetailViewModel>()
    private var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()

        when (type) {
            FOLLOWERS -> viewModel.userFollowersResult.observe(viewLifecycleOwner, ::manageResultFollows)
            FOLLOWING -> viewModel.userFollowingResult.observe(viewLifecycleOwner, ::manageResultFollows)
        }
    }

    private fun setupRV() {
        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }
    }

    private fun manageResultFollows(state: Result) {
        when (state) {
            is Result.isSuccess<*> -> adapter.setData(state.data as MutableList<ResponseUserGithub.Item>)
            is Result.isError -> showToast(state.exception.message.toString())
            is Result.isLoad -> binding?.progressBar?.isVisible = state.isLoading
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowFragment().apply {
            this.type = type
        }
    }
}