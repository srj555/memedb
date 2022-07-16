package com.srdroid.memedb.presentation.meme_search

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.srdroid.memedb.R
import com.srdroid.memedb.databinding.FragmentMemeSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MemeSearchFragment : Fragment(), SearchView.OnQueryTextListener {

    // search adapter
    private val searchAdapter = MemeSearchAdapter()

    // view model
    private val viewModel: MemeSearchViewModel by viewModels()

    // ui
    private lateinit var binding: FragmentMemeSearchBinding
    private lateinit var rootView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemeSearchBinding.inflate(inflater, container, false)
        // get memes init
        if (!this::rootView.isInitialized)
            getMemes()
        rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // set updater
        binding.memeSearchRecycler.apply {
            adapter = searchAdapter
        }

        // observe and update UI based on result
        updateUIBasedOnResult()

        // set item click listener
        onItemClicked()
    }

    /**
     * Update UI based on results
     */
    private fun updateUIBasedOnResult() {
        // Observe Result
        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.getMemesState.collect {
                // On Loading State
                onLoadingState(it)
                // On Error State
                onErrorState(it)
                //On Success State
                onSuccessState(it)
            }
        }
    }

    /**
     * Handle Success State
     */
    private fun onSuccessState(uiState: MemeSearchState) {
        uiState.data?.let { result ->
            if (result.isEmpty()) {
                binding.nothingFound.visibility = View.VISIBLE
            } else
                binding.nothingFound.visibility = View.GONE

            binding.progressSearch.visibility = View.GONE
            searchAdapter.setContentList(result.toMutableList())
        }
    }

    /**
     * Handle Error State
     */
    private fun onErrorState(uiState: MemeSearchState) {
        if (uiState.error != null) {
            binding.nothingFound.visibility = View.GONE
            binding.progressSearch.visibility = View.GONE
            Toast.makeText(requireContext(), uiState.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Handle Loading State
     */
    private fun onLoadingState(uiState: MemeSearchState) {
        if (uiState.isLoading) {
            binding.nothingFound.visibility = View.GONE
            binding.progressSearch.visibility = View.VISIBLE
        }
    }

    /**
     * Item Click listener
     */
    private fun onItemClicked() {
        searchAdapter.itemClickListener {
            findNavController().navigate(
                MemeSearchFragmentDirections.actionMemeSearchFragmentToMemeDetailsFragment(
                    it.id, it.name
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_memes, menu)
        val searchView = (menu.findItem(R.id.menuSearch).actionView as SearchView)
        // set listener
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

    /**
     * Invoke memes api
     */
    private fun getMemes() {
        viewModel.getMemes()
    }

    /**
     * On search query
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.filterMemes(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { viewModel.filterMemes(it) }
        return true
    }

}