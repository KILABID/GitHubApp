package com.bangkit.submissionAwal.ui.followFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionAwal.R
import com.bangkit.submissionAwal.databinding.FragmentFollowingBinding
import com.bangkit.submissionAwal.ui.detailPage.DetailActivity.Companion.username
import com.bangkit.submissionAwal.ui.adapter.DetailsAdapter
import com.bangkit.submissionAwal.ui.detailPage.DetailViewModel


class FollowingFragment : Fragment(R.layout.fragment_following) {
    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding: FragmentFollowingBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowingBinding.bind(view)

//        val recyclerView: RecyclerView = view.findViewById(R.id.rvFollower)
        val recyclerView = binding.rvFollowing
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        detailsAdapter = DetailsAdapter()
        recyclerView.adapter = detailsAdapter


        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getFollowing(username)
        detailViewModel.userFollowing.observe(viewLifecycleOwner) { following ->
            // Update the adapter's dataset with the new follower data
            detailsAdapter.submitList(following)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}