package com.srdroid.memedb.usecase

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse.getMemesModel
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.data.repository.MemeDetailsRepositoryImpl
import com.srdroid.memedb.domain.errorhandler.GeneralErrorHandlerImpl
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.usecases.GetMemeDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
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
            memeDetailsRepository,
            MemeModelMapper(),
            GeneralErrorHandlerImpl()
        )
    }

    @Test
    fun `Given response when invoke details use case expect ui state value contains id`() =
        runTest {
            coEvery { memeDetailsRepository.getMemeDetails(ID) } returns getMemesModel()

            val first = memeDetailsUseCase.invoke(ID).drop(1).first()

            assertEquals(first.data?.get(0)?.id, ID)
        }

    @Test
    fun `Given http error when invoke details use case expect null data`() = runTest {

        val httpException = HttpException(
            Response.error<List<MemeDTO>>(
                HttpURLConnection.HTTP_NOT_FOUND,
                mock()
            )
        )
        coEvery { memeDetailsRepository.getMemeDetails(ID) }.throws(httpException)

        val first = memeDetailsUseCase.invoke(ID).first()

        Assert.assertNull(first.data)
    }
}
