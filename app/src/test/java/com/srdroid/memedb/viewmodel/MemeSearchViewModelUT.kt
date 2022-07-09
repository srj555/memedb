package com.srdroid.memedb.viewmodel

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.core.MockResponse
import com.srdroid.memedb.domain.use_case.GetMemeUseCase
import com.srdroid.memedb.presentation.mapper.MemeMapper
import com.srdroid.memedb.presentation.meme_search.MemeSearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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

    private lateinit var memeSearchViewModel: MemeSearchViewModel

    @Before
    fun setUp() {
        memeSearchViewModel = MemeSearchViewModel(getMemesUseCase,mapper)
    }

    @Test
    fun testMemeSearchViewModel_getMeme_success() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { getMemesUseCase.invoke() } returns MockResponse.getResourceData()
            memeSearchViewModel.getMemes()
            assertEquals(memeSearchViewModel.getMemesState.value.data?.get(0)?.id, ID)
        }
    }

    @Test
    fun testMemeSearchViewModel_getMeme_failure() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { getMemesUseCase.invoke() } returns MockResponse.getDataFailureMock()
            memeSearchViewModel.getMemes()
            assertNotNull(memeSearchViewModel.getMemesState.value.error)
        }
    }

    @Test
    fun testMemeSearchViewModel_filter() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { getMemesUseCase.invoke() } returns MockResponse.getResourceData()
            memeSearchViewModel.getMemes()
            memeSearchViewModel.filterMemes("d")
            assertEquals(memeSearchViewModel.getMemesState.value.data?.get(0)?.id, ID)
        }
    }

}
