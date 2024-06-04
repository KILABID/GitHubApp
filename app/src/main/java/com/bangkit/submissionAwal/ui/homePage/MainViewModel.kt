package com.bangkit.submissionAwal.ui.homePage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submissionAwal.data.remote.api.ApiConfig
import com.bangkit.submissionAwal.data.remote.response.ItemsItem
import com.bangkit.submissionAwal.data.remote.response.ListUserResponse
import com.bangkit.submissionAwal.ui.settings.SettingsPreferences
import retrofit2.Call
import retrofit2.Callback

class MainViewModel(private val settingsPreferences: SettingsPreferences) : ViewModel() {

    private var _listUsers = MutableLiveData<List<ItemsItem?>?>()
    var listUsers: LiveData<List<ItemsItem?>?> = _listUsers


    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    companion object {
        private const val TAG = "MainViewModel"
    }


    fun setSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(
                call: Call<ListUserResponse>,
                response: retrofit2.Response<ListUserResponse>
            ) {
                _isLoading.value = false
                val users = response.body()
                if (response.isSuccessful && users != null) {
                    _listUsers.value = response.body()?.items
                    if (response.body()?.items?.size?.equals(0) == true) {
                        _toastError.value = "User not found"
                    }
                } else {
                    Log.d(TAG, "Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                _isLoading.value = false
                _toastError.value = t.message.toString()
                Log.d(TAG, "Failure: ${t.message.toString()}")
            }
        })
    }

}