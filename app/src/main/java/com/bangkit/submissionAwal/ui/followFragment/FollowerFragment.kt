package com.bangkit.submissionAwal.ui.followFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionAwal.R
import com.bangkit.submissionAwal.databinding.FragmentFollowerBinding
import com.bangkit.submissionAwal.ui.detailPage.DetailActivity.Companion.username
import com.bangkit.submissionAwal.ui.detailPage.DetailViewModel
import com.bangkit.submissionAwal.ui.adapter.DetailsAdapter


class FollowerFragment : Fragment(R.layout.fragment_follower) {

    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentFollowerBinding? = null
    private val binding: FragmentFollowerBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowerBinding.bind(view)

//        val recyclerView: RecyclerView = view.findViewById(R.id.rvFollower)
        val recyclerView = binding.rvFollower
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        detailsAdapter = DetailsAdapter()
        recyclerView.adapter = detailsAdapter


        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getFollower(username)
        detailViewModel.userFollower.observe(viewLifecycleOwner) { followers ->
            // Update the adapter's dataset with the new follower data
            detailsAdapter.submitList(followers)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}