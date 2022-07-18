package com.srdroid.memedb.viewmodel

import android.content.Context
import com.srdroid.memedb.core.MockResponse
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.domain.usecases.GetMemeUseCase
import com.srdroid.memedb.presentation.mapper.ErrorViewMapper
import com.srdroid.memedb.presentation.mapper.MemeMapper
import com.srdroid.memedb.presentation.memesearch.MemeSearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class MemeSearchViewModelUT {


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getMemesUseCase = mockk<GetMemeUseCase>()
    private val mapper = MemeMapper()
    private val mContextMock = mockk<Context>(relaxed = true)
    private val errorViewMapper = ErrorViewMapper(mContextMock)

    private lateinit var memeSearchViewModel: MemeSearchViewModel

    @Before
    fun setUp() {
        memeSearchViewModel = MemeSearchViewModel(getMemesUseCase, mapper, errorViewMapper)
    }

    @Test
    fun when_VMGetMeme_Expect_MemeStateData() = runTest {
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getResourceData()
        // WHEN
        memeSearchViewModel.getMemes()
        val job = launch(UnconfinedTestDispatcher()) {
            // collect is needed for state flow
            memeSearchViewModel.getMemesState.collect()
            // THEN
            assertNotNull(memeSearchViewModel.getMemesState.value.data)
        }
        job.cancel()
    }


    @Test
    fun given_Error_when_VMGetMeme_Expect_MemeStateError() = runTest {
        // GIVEN Error State
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getDataFailureMock()
        // WHEN
        memeSearchViewModel.getMemes()
        val job = launch(UnconfinedTestDispatcher()) {
            // collect is needed for state flow
            memeSearchViewModel.getMemesState.collect()
            // THEN
            assertNotNull(memeSearchViewModel.getMemesState.value.error)
        }
        job.cancel()
    }

    @Test
    fun given_Error_when_VMGetMeme_Expect_MemeUnknownStateError() = runTest {
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getDataFailureUnknown()
        // WHEN
        memeSearchViewModel.getMemes()
        val job = launch(UnconfinedTestDispatcher()) {
            // collect is needed for state flow
            memeSearchViewModel.getMemesState.collect()
            // THEN
            assertNotNull(memeSearchViewModel.getMemesState.value.error)
        }
        job.cancel()
    }

    @Test
    fun when_VMfilter_Expect_FilteredData() = runTest {
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getResourceData()
        // WHEN
        memeSearchViewModel.getMemes()
        memeSearchViewModel.filter = "d"
        val job = launch(UnconfinedTestDispatcher()) {
            // collect is needed for state flow
            memeSearchViewModel.getMemesState.collect()
            // THEN
            assertTrue(
                memeSearchViewModel.getMemesState.value.data?.get(0)?.name?.contains("d") ?: false
            )
        }
        memeSearchViewModel.resetFilter()
        job.cancel()
    }

}
