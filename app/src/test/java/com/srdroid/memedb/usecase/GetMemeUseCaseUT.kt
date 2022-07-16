package com.srdroid.memedb.usecase

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse
import com.srdroid.memedb.core.MockResponse.getMemesModel
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.data.error.GeneralErrorHandlerImpl
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.use_case.GetMemeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
        )
    }

    @Test
    fun when_UCGetMeme_Expect_Data() = runTest {
        // GIVEN
        coEvery { memeSearchRepository.getMemes() } returns getMemesModel()
        // WHEN
        val first = searchMemesUseCase.invoke().first()
        // THEN
        assertEquals(first.data?.get(0)?.id, ID)
    }

    @Test
    fun given_Error_when_UCGetMeme_Expect_EmptyData() = runTest {
        // GIVEN
        coEvery { memeSearchRepository.getMemes() } returns MockResponse.getMemesModelFailure()
        // WHEN
        val first = searchMemesUseCase.invoke().first()
        // THEN
        assertNull(first.data)
    }
}
