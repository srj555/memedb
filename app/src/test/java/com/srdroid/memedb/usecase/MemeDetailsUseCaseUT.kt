package com.srdroid.memedb.usecase

import com.srdroid.memedb.core.ID
import com.srdroid.memedb.core.MockResponse.getMemesModel
import com.srdroid.memedb.core.MockResponse.getMemesModelFailure
import com.srdroid.memedb.core.TestCoroutineRule
import com.srdroid.memedb.data.error.GeneralErrorHandlerImpl
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.data.repository.MemeDetailsRepositoryImpl
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.use_case.GetMemeDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        )
    }

    @Test
    fun when_UCGetMemeDetails_Expect_Data() = runTest {
        // GIVEN
        coEvery { memeDetailsRepository.getMemeDetails(ID) } returns getMemesModel()
        // WHEN
        val first = memeDetailsUseCase.invoke(ID).first()
        // THEN
        assertEquals(first.data?.get(0)?.id, ID)
    }

    @Test
    fun given_Error_when_UCGetMemeDetails_Expect_Error() = runTest {

        coEvery { memeDetailsRepository.getMemeDetails(ID) } returns getMemesModelFailure()
        // WHEN
        val first = memeDetailsUseCase.invoke(ID).first()
        // THEN
        Assert.assertNull(first.data)
    }
}
