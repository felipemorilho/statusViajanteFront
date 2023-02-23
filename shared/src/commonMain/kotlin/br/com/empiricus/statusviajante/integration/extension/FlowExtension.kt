package br.com.digitalhouse.dhwallet.extension

import br.com.digitalhouse.dhwallet.util.DataResult
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen

fun <T : Any> Flow<DataResult<T>>.updateState() =
    retryWhen { cause, attempt ->
        if (cause is IOException && attempt < 3) {
            delay(5000)
            true
        } else {
            false
        }
    }
        .onStart { emit(DataResult.Loading) }
        .catch { emit(DataResult.Error(it)) }