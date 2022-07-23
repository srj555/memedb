package com.srdroid.memedb.usecase

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse.getMemesModel
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.errorhandler.GeneralErrorHandlerImpl
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.usecases.GetMemeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
class GetMemeUseCaseUT {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val memeSearchRepository = mockk<MemeRepositoryImpl>()

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

        assertEquals(ID,first.data?.get(0)?.id)
    }

    @Test
    fun `Given http error when invoke memes use case expect null data`() = runTest {
        val httpException = HttpException(
            Response.error<List<MemeDTO>>(
                HttpURLConnection.HTTP_NOT_FOUND,
                mock()
            )
        )
        coEvery { memeSearchRepository.getMemes() }.throws(httpException)

        val first = searchMemesUseCase.invoke().first()

        assertNull(first.data)
    }
}
