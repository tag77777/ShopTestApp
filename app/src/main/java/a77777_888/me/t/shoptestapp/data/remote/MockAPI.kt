package a77777_888.me.t.shoptestapp.data.remote

import a77777_888.me.t.shoptestapp.data.remote.entities.MockResponse
import retrofit2.Response
import retrofit2.http.GET

interface MockAPI {
    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getData(): Response<MockResponse>

    companion object {
        const val BASE_URL = "https://run.mocky.io/"
    }
}