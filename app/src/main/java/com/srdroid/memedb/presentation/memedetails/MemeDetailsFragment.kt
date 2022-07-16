package com.srdroid.memedb.presentation.memedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.srdroid.memedb.databinding.FragmentMemeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MemeDetailsFragment : Fragment() {

    // ui
    private lateinit var _binding: FragmentMemeDetailsBinding

    // view model
    private val viewModel: MemeDetailsViewModel by viewModels()

    // nav args
    private val args: MemeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemeDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        args.memeId?.let {
            viewModel.getMemeDetails(it)
        }

        updateUIBasedOnResult()

    }

    private fun updateUIBasedOnResult() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.memeDetails.collect { result ->
                if (result.isLoading) {
                    updateProgress(true)
                }
                // error state
                if (result.error != null) {
                    updateProgress(false)
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
                // success state
                result.data?.let {
                    updateProgress(false)
                    _binding.memeDetails = it
                }
            }

        }
    }

    /**
     * Detail progress UI update
     */
    private fun updateProgress(showProgress: Boolean) {
        if (showProgress) {
            _binding.detailsSV.visibility = View.INVISIBLE
            _binding.progressDetail.visibility = View.VISIBLE
        } else {
            _binding.detailsSV.visibility = View.VISIBLE
            _binding.progressDetail.visibility = View.INVISIBLE
        }
    }

}