package com.srdroid.memedb.viewmodel

import android.content.Context
import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.domain.usecases.GetMemeDetailsUseCase
import com.srdroid.memedb.presentation.mapper.ErrorViewMapper
import com.srdroid.memedb.presentation.mapper.MemeMapper
import com.srdroid.memedb.presentation.memedetails.MemeDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MemeDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getDetailsUseCase = mockk<GetMemeDetailsUseCase>()
    private val mapper = MemeMapper()

    private val memeDetailsViewModel: MemeDetailsViewModel by lazy {
        MemeDetailsViewModel(getDetailsUseCase, mapper, errorViewMapper)
    }

    private val mContextMock = mockk<Context>(relaxed = true)
    private val errorViewMapper = ErrorViewMapper(mContextMock)

    @Test
    fun `Given response When get details of id Expect ui state value contains id`() = runTest {
        coEvery { getDetailsUseCase.invoke(ID) } returns MockResponse.getResourceData()

        memeDetailsViewModel.getMemeDetails(ID)

        assertEquals(memeDetailsViewModel.memeDetailsUiState.value.data?.id, ID)
    }

    @Test
    fun `Given error when get details of id expect memeDetails state error`() = runTest {
        coEvery { getDetailsUseCase.invoke(ID) } returns MockResponse.getDataFailureMock()

        memeDetailsViewModel.getMemeDetails(ID)

        assertNotNull(memeDetailsViewModel.memeDetailsUiState.value.error)

    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}
