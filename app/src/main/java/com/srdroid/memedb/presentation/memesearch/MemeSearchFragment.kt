package com.srdroid.memedb.presentation.memesearch

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.srdroid.memedb.R
import com.srdroid.memedb.databinding.FragmentMemeSearchBinding
import com.srdroid.memedb.presentation.model.MemeItemUIModel
import com.srdroid.memedb.presentation.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MemeSearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private val searchAdapter = MemeSearchAdapter()
    private val viewModel: MemeSearchViewModel by viewModels()
    private lateinit var binding: FragmentMemeSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.memeSearchRecycler.apply {
            adapter = searchAdapter
        }

        observeResultState()

        onItemClicked()
    }

    private fun observeResultState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getMemesUiState.collectLatest { uiState ->
                    onInitialState(uiState)
                    onLoadingState(uiState)
                    onErrorState(uiState)
                    onSuccessState(uiState)
                }
            }
        }
    }

    private fun onInitialState(result: UiState<List<MemeItemUIModel>>) {
        if (result.isInitialState) {
            getMemes()
        }
    }

    private fun onSuccessState(uiState: UiState<List<MemeItemUIModel>>) {
        uiState.data?.let { result ->
            if (result.isEmpty()) {
                binding.nothingFound.visibility = View.VISIBLE
            } else
                binding.nothingFound.visibility = View.GONE

            binding.progressSearch.visibility = View.GONE
            searchAdapter.setContentList(result.toMutableList())
        }
    }

    private fun onErrorState(uiState: UiState<List<MemeItemUIModel>>) {
        uiState.error?.let {
            binding.nothingFound.visibility = View.GONE
            binding.progressSearch.visibility = View.GONE
            Toast.makeText(requireContext(), uiState.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoadingState(uiState: UiState<List<MemeItemUIModel>>) {
        if (uiState.isLoading) {
            binding.nothingFound.visibility = View.GONE
            binding.progressSearch.visibility = View.VISIBLE
        }
    }

    private fun onItemClicked() {
        searchAdapter.itemClickListener {
            findNavController().navigate(
                MemeSearchFragmentDirections.actionMemeSearchFragmentToMemeDetailsFragment(
                    it.id, it.name
                )
            )
            viewModel.filterMemes("")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_memes, menu)
        val searchView = (menu.findItem(R.id.menuSearch).actionView as SearchView)
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuRefresh -> {
                getMemes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMemes() {
        viewModel.getMemes()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.filterMemes(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { viewModel.filterMemes(it) }
        return true
    }
}