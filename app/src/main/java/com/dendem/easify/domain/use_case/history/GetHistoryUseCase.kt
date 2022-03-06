package com.dendem.easify.domain.use_case.history

import com.dendem.easify.common.Result
import com.dendem.easify.data.remote.dto.HistoryDTO
import com.dendem.easify.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(): Flow<Result<HistoryDTO>> = flow {
        try {
            emit(Result.Loading())
            val history = repository.getUserHistory()
            emit(Result.Success(history))
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
