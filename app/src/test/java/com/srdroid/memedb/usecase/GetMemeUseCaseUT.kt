package com.srdroid.memedb.usecase

import com.srdroid.memedb.common.TestCoroutineRule
import com.srdroid.memedb.common.MockResponse.getMemesModel
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.use_case.GetMemeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class GetMemeUseCaseUT {


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val memeSearchRepository = mockk<MemeRepositoryImpl>()
    private val searchMemesUseCase by lazy {
        GetMemeUseCase(
            memeSearchRepository
        )
    }

    @Test
    fun testMemeSearchUseCases_getMemeSearch_Completed() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { memeSearchRepository.getMemes() } returns getMemesModel()
            val first = searchMemesUseCase.invoke().first()
            assertEquals(first.data?.get(0)?.id, "1")
        }

    @Test
    fun testMemeSearchUseCases_getMemeSearch_Failure() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { memeSearchRepository.getMemes() } returns any()
            val first = searchMemesUseCase.invoke().first()
            assertNull(first.data)
        }
}
