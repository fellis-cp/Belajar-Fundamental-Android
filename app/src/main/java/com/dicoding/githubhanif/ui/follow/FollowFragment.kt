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
    var type = 0
    private val viewModel by activityViewModels<DetailViewModel> ()
    private val adapter by lazy { UserAdapter{} }
    private var binding : FragmentFollowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when (type) {
            FOLLOWER -> {
                viewModel.userFollowersResult.observe(viewLifecycleOwner, this::manageFollowsResult)
            }

            FOLLOWING -> {
                viewModel.userFollowingResult.observe(viewLifecycleOwner, this::manageFollowsResult)
            }
        }
    }


    private fun manageFollowsResult(state : Result){
        when(state){
            is Result.isSuccess<*> -> {
                adapter.setData(state.data as MutableList<ResponseUserGithub.Item>)
            }
            is Result.isError -> {
                Toast.makeText(requireActivity() , state.exception.message.toString() , Toast.LENGTH_SHORT).show()
            }
            is Result.isLoad ->{
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object{

        const val FOLLOWING = 100
        const val FOLLOWER = 101
        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }
    }





}