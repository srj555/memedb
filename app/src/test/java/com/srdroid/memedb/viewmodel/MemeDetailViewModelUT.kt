package com.srdroid.memedb.viewmodel

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.domain.use_case.GetMemeDetailsUseCase
import com.srdroid.memedb.presentation.mapper.MemeMapper
import com.srdroid.memedb.presentation.meme_details.MemeDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
class MemeDetailViewModelUT {


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getDetailsUseCase = mockk<GetMemeDetailsUseCase>()
    private val mapper = MemeMapper()

    private lateinit var memeDetailsViewModel: MemeDetailsViewModel

    @Before
    fun setUp() {
        memeDetailsViewModel = MemeDetailsViewModel(getDetailsUseCase, mapper)
    }

    @Test
    fun when_GetMemeDetails_Expect_MemeDetailsData() = runTest {
        //GIVEN
        coEvery { getDetailsUseCase.invoke(ID) } returns MockResponse.getResourceData()
        // WHEN
        memeDetailsViewModel.getMemeDetails(ID)
        // THEN
        assertEquals(memeDetailsViewModel.memeDetails.value.data?.id, ID)
    }

    @Test
    fun given_Error_when_GetMemeDetails_Expect_MemeDetailsError() = runTest {
        //GIVEN
        coEvery { getDetailsUseCase.invoke(ID) } returns MockResponse.getDataFailureMock()
        // WHEN
        memeDetailsViewModel.getMemeDetails(ID)
        // THEN
        assertNotNull(memeDetailsViewModel.memeDetails.value.error)

    }

}
