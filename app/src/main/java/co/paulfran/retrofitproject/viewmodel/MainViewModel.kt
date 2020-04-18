package co.paulfran.retrofitproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.paulfran.retrofitproject.model.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        GlobalScope.launch(Dispatchers.Main) {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }

    val apiResponse = MutableLiveData<List<Item>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun fetchData() {
        loading.value = true

        ApiCallService.call().enqueue(object: Callback<ApiCallResponse> {
            override fun onFailure(call: Call<ApiCallResponse>, t: Throwable) {
                onError(t.localizedMessage)

            }

            override fun onResponse(
                call: Call<ApiCallResponse>,
                response: Response<ApiCallResponse>
            ) {
                val body = response.body()
                apiResponse.value = body?.flatten()
                error.value = null
                loading.value = false
            }

        })
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}