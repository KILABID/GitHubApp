package com.bangkit.submissionAwal.data.remote.api

import com.bangkit.submissionAwal.data.remote.response.DetailResponse
import com.bangkit.submissionAwal.data.remote.response.FollowerResponse
import com.bangkit.submissionAwal.data.remote.response.ListUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<ListUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String?
    ): Call<DetailResponse>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username")
        username: String?
    ): Call<List<FollowerResponse>>


    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String?
    ): Call<List<FollowerResponse>>

}