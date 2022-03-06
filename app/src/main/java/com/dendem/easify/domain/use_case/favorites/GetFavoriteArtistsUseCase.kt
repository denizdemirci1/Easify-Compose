package com.dendem.easify.domain.use_case.favorites

import com.dendem.easify.common.Result
import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.domain.repository.PersonalizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFavoriteArtistsUseCase @Inject constructor(
    private val repository: PersonalizationRepository
) {
    operator fun invoke(
        timeRange: String,
        limit: Int = 50
    ): Flow<Result<TopArtistsDTO>> = flow {
        try {
            emit(Result.Loading())
            val topArtists = repository.getTopArtists(timeRange, limit)
            emit(Result.Success(topArtists))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = e.localizedMessage ?: "An unexpected error occured",
                    code = e.code()
                )
            )
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
