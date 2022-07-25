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
    fun `Given response data when getMemes expect getMemeState value`() = runTest {
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getResourceData()

        memeSearchViewModel.getMemes()
        memeSearchViewModel.initialServiceInvoked = true

        assertNotNull(memeSearchViewModel.getMemesState.value.data)
    }

    @Test
    fun `Given error data when getMemes expect getMemeState error`() = runTest {
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getDataFailureMock()

        memeSearchViewModel.getMemes()

        assertNotNull(memeSearchViewModel.getMemesState.value.error)
    }

    @Test
    fun `Given search filter when getMemes expect result contains filtered data`() = runTest {
        coEvery { getMemesUseCase.invoke() } returns MockResponse.getResourceData()

        memeSearchViewModel.getMemes()
        memeSearchViewModel.filterMemes("d")

        assertTrue(
            memeSearchViewModel.getMemesState.value.data?.get(0)?.name?.contains("d") ?: false
        )
    }

}
