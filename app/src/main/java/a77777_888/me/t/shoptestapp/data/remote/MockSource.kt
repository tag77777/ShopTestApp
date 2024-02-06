package a77777_888.me.t.shoptestapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockSource {

    companion object{
        val mockAPI: MockAPI = Retrofit.Builder()
            .baseUrl(MockAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MockAPI::class.java)
    }

}