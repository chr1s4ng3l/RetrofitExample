package com.example.retrofitexample

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getDogByBreeds(@Url url: String): Response<DogsItems>


    companion object{

        val BASE_URL = "https://dog.ceo/api/breed/"

    }

}