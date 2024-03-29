package com.srdroid.memedb.usecase

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse.getMemesModel
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.errorhandler.GeneralErrorHandlerImpl
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.usecases.GetMemeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class GetMemeUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val memeSearchRepository = mockk<MemeRepositoryImpl>(relaxed = true)

    private val searchMemesUseCase by lazy {
        GetMemeUseCase(
            memeSearchRepository,
            MemeModelMapper(),
            GeneralErrorHandlerImpl()
        )
    }

    @Test
    fun `Given response data when invoke memes use case expect result has data`() = runTest {
        coEvery { memeSearchRepository.getMemes() } returns getMemesModel()

        val first = searchMemesUseCase.invoke().drop(1).first()

        assertEquals(ID, first.data?.get(0)?.id)
    }

    @Test
    fun `Given http error when invoke memes use case expect null data`() = runTest {
        coEvery { memeSearchRepository.getMemes() }.throws(Throwable("Test"))

        val first = searchMemesUseCase.invoke().drop(1).first()

        assertNull(first.data)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
