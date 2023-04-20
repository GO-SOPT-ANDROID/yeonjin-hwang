package org.android.go.sopt.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.MyAdapter
import org.android.go.sopt.RepoTitleAdapter
import org.android.go.sopt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = requireNotNull(_binding) { "FragmentHomeBinding error - null" }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        val repoTitleAdapter = RepoTitleAdapter()
        val myAdapter = MyAdapter(requireContext())

        with(binding.rvMain) {
            adapter = ConcatAdapter(repoTitleAdapter, myAdapter)
            layoutManager = LinearLayoutManager(context)
        }
    }

}