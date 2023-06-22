package org.android.go.sopt.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.presentation.home.adapter.MyAdapter
import org.android.go.sopt.presentation.home.adapter.RepoTitleAdapter
import org.android.go.sopt.data.api.ServicePool
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = requireNotNull(_binding) { "FragmentHomeBinding error - null" }
    private var followerService = ServicePool.followerService
    private lateinit var myAdapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        completeLoadingFollower()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        val repoTitleAdapter = RepoTitleAdapter()
        myAdapter = MyAdapter(requireContext())

        with(binding.rvMain) {
            adapter = ConcatAdapter(repoTitleAdapter, myAdapter)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun completeLoadingFollower() {
        followerService.loadFollower()
            .enqueueUtil(
                onSuccess = {
                    Log.d("Follower API connection", "200")
                    it.let {
                        myAdapter.submitList(it.data)
                    }
                },
                onError = {
                    when(it) {
                        304 -> requireContext().showToast("Not modified")
                        401 -> requireContext().showToast("Requires authentication")
                        403 -> requireContext().showToast("Forbidden")
                    }
                }
            )
    }
}