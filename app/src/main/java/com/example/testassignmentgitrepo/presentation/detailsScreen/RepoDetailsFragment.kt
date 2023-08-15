package com.example.testassignmentgitrepo.presentation.detailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testassignmentgitrepo.databinding.FragmentRepositoryDetailsBinding
import com.example.testassignmentgitrepo.presentation.ReposViewModel


class RepoDetailsFragment : Fragment() {
    private var _binding: FragmentRepositoryDetailsBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!
    private lateinit var viewModel: ReposViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)

        val toolbar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "My Title"
        toolbar?.setHomeButtonEnabled(true);

        viewModel = ViewModelProvider(this)[ReposViewModel::class.java]


//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//
//        (activity as AppCompatActivity).supportActionBar?.setNavigationOnClickListener { view ->
//            view.findNavController().navigateUp()
//        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}