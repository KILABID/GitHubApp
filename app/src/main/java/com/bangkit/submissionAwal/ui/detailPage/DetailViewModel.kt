package com.bangkit.submissionAwal.ui.detailPage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.submissionAwal.data.local.entity.FavoritEntity
import com.bangkit.submissionAwal.data.local.repository.FavoriteRepository
import com.bangkit.submissionAwal.data.remote.api.ApiConfig
import com.bangkit.submissionAwal.data.remote.response.DetailResponse
import com.bangkit.submissionAwal.data.remote.response.FollowerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private var _userDetail = MutableLiveData<DetailResponse?>()
    var userDetail: LiveData<DetailResponse?> = _userDetail

    private var _userFollower = MutableLiveData<List<FollowerResponse?>?>()
    var userFollower: LiveData<List<FollowerResponse?>?> = _userFollower

    private var _userFollowing = MutableLiveData<List<FollowerResponse?>?>()
    var userFollowing: LiveData<List<FollowerResponse?>?> = _userFollowing

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _toastErrorDetail = MutableLiveData<String>()
    val toastErrorDetail: LiveData<String> = _toastErrorDetail

    private val repository: FavoriteRepository = FavoriteRepository(application)


    fun getUserDetail(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse?> {
            override fun onResponse(
                call: Call<DetailResponse?>,
                response: Response<DetailResponse?>
            ) {
                _isLoading.value = false
                val detailResponse = response.body()
                if (response.isSuccessful && detailResponse != null) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse?>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "Failure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowerResponse>> {

            override fun onResponse(
                call: Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                _isLoading.value = false
                val followResponse = response.body()
                if (response.isSuccessful && followResponse != null) {
                    _userFollower.value = followResponse
                } else {
                    _toastErrorDetail.value = response.message()
                }
            }

            override fun onFailure(call: Call<List<FollowerResponse>>, t: Throwable) {
                _isLoading.value = false
                _toastErrorDetail.value = t.message.toString()
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<FollowerResponse>> {

            override fun onResponse(
                call: Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                _isLoading.value = false
                val followResponse = response.body()
                if (response.isSuccessful && followResponse != null) {
                    _userFollowing.value = followResponse
                } else {
                    _toastErrorDetail.value = response.message()
                }
            }

            override fun onFailure(call: Call<List<FollowerResponse>>, t: Throwable) {
                _isLoading.value = false
                _toastErrorDetail.value = t.message.toString()
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addFav(fav: FavoritEntity) {
        repository.addFav(fav)
    }

    fun removeFav(fav: FavoritEntity) {
        repository.removeFav(fav)
    }

    fun getFavUser(): LiveData<List<FavoritEntity>> = repository.getFavUser()


}