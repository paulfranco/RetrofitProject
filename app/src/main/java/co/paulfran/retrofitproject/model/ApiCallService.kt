package co.paulfran.retrofitproject.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiCallService {

    private val BASE_URL = "https://us-central1-apis2-e78c3.cloudfunctions.net/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiCall::class.java)

    // expose the network communication to whoever needs it
    fun call() =
        api.callGet()
}