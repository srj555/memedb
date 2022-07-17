package com.srdroid.memedb.presentation.memedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.srdroid.memedb.databinding.FragmentMemeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MemeDetailsFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentMemeDetailsBinding

    // view model
    private val viewModel: MemeDetailsViewModel by viewModels()

    // nav args
    private val args: MemeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Invoke Service based on meme ID
        args.memeId?.let {
            viewModel.getMemeDetails(it)
        }
        // Observe Data and update result
        updateUIBasedOnResult()
    }

    /**
     * Collect Result
     */
    private fun updateUIBasedOnResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.memeDetails.collect { result ->
                    // on Loading State
                    onLoadingState(result)

                    // success state
                    onSuccessState(result)

                    // on error state
                    onErrorState(result)
                }
            }
        }
    }

    /**
     * On Success State
     */
    private fun onSuccessState(result: MemeDetailsState) {
        // error state
        result.data?.let {
            updateProgress(false)
            binding.memeDetails = it
        }
    }

    /**
     * On Loading State
     */
    private fun onLoadingState(result: MemeDetailsState) {
        if (result.isLoading) {
            updateProgress(true)
        }
    }

    /**
     * On Error State
     */
    private fun onErrorState(result: MemeDetailsState) {
        // error state
        if (result.error != null) {
            updateProgress(false)
            Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Detail progress UI update
     */
    private fun updateProgress(showProgress: Boolean) {
        if (showProgress) {
            binding.detailsSV.visibility = View.INVISIBLE
            binding.progressDetail.visibility = View.VISIBLE
        } else {
            binding.detailsSV.visibility = View.VISIBLE
            binding.progressDetail.visibility = View.INVISIBLE
        }
    }

}