package com.srdroid.memedb.usecase

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse.getMemesModel
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.data.repository.MemeDetailsRepositoryImpl
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.use_case.GetMemeDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class MemeDetailsUseCaseUT {


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val memeDetailsRepository = mockk<MemeDetailsRepositoryImpl>()
    private val memeDetailsUseCase by lazy {
        GetMemeDetailsUseCase(
            memeDetailsRepository
        )
    }

    @Test
    fun testMemeSearchUseCases_getMemeSearch_Completed() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { memeDetailsRepository.getMemeDetails(ID) } returns getMemesModel()
            val first = memeDetailsUseCase.invoke(ID).first()
            assertEquals(first.data?.get(0)?.id, ID)
        }

    @Test
    fun testMemeSearchUseCases_getMemeSearch_Failure() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            val httpException = HttpException(
                Response.error<List<MemeDTO>>(
                    HttpURLConnection.HTTP_NOT_FOUND,
                    mock()
                )
            )
            val domainData = listOf<MemeModel>()
            coEvery { memeDetailsRepository.getMemeDetails(ID) }.throws(httpException)
            val first = memeDetailsUseCase.invoke(ID).first()
            assertSame(domainData, first.data)
        }
}
