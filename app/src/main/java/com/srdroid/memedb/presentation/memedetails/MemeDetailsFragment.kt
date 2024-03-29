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
import com.srdroid.memedb.presentation.model.MemeItemUIModel
import com.srdroid.memedb.presentation.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MemeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMemeDetailsBinding
    private val viewModel: MemeDetailsViewModel by viewModels()
    private val args: MemeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeResultState()
    }

    private fun observeResultState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.memeDetailsUiState.collectLatest { result ->
                    onInitialState(result)
                    onLoadingState(result)
                    onSuccessState(result)
                    onErrorState(result)
                }
            }
        }
    }

    private fun onInitialState(result: UiState<MemeItemUIModel>) {
        if (result.isInitialState) {
            args.memeId?.let {
                viewModel.getMemeDetails(it)
            }
        }
    }

    private fun onSuccessState(result: UiState<MemeItemUIModel>) {
        result.data?.let {
            updateProgress(false)
            binding.memeDetails = it
        }
    }

    private fun onLoadingState(result: UiState<MemeItemUIModel>) {
        if (result.isLoading) {
            updateProgress(true)
        }
    }

    private fun onErrorState(result: UiState<MemeItemUIModel>) {
        result.error?.let {
            updateProgress(false)
            Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

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