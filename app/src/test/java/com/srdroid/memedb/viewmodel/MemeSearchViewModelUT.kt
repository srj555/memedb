package com.srdroid.memedb.viewmodel

import com.srdroid.memedb.common.TestCoroutineRule
import com.srdroid.memedb.common.MockResponse
import com.srdroid.memedb.domain.repository.MemeRepository
import com.srdroid.memedb.domain.use_case.GetMemeUseCase
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
import org.mockito.kotlin.anyOrNull

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class MemeSearchViewModelUT {


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val memeSearchRepository = mockk<MemeRepository>()
    private val getMemesUseCase by lazy {
        GetMemeUseCase(
            memeSearchRepository
        )
    }

    private lateinit var memeSearchViewModel: MemeSearchViewModel

    @Before
    fun setUp() {
        memeSearchViewModel = MemeSearchViewModel(getMemesUseCase)
    }

    @Test
    fun testMemeSearchViewModel_searchMeme_success() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { memeSearchRepository.getMemes() } returns MockResponse.getMemesModel()
            memeSearchViewModel.getMemes()
            assertEquals(memeSearchViewModel.memeSearchList.value.data?.get(0)?.id, "1")
        }
    }

    @Test
    fun testMemeSearchViewModel_searchMeme_failure() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { memeSearchRepository.getMemes() } returns anyOrNull()
            memeSearchViewModel.getMemes()
            assertNotNull(memeSearchViewModel.memeSearchList.value.error)
        }
    }
}
