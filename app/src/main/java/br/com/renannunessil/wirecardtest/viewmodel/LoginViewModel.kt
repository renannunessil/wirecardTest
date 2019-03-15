package br.com.renannunessil.wirecardtest.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.hardware.camera2.CaptureFailure
import android.util.Log
import br.com.renannunessil.wirecardtest.R
import br.com.renannunessil.wirecardtest.data.model.LoginRequest
import br.com.renannunessil.wirecardtest.data.model.LoginResponse
import br.com.renannunessil.wirecardtest.repository.LoginRepository
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LoginRepository.LoginRepositoryProvider.provideLoginRepository()
    private lateinit var loginResponse: MutableLiveData<LoginResponse>
    private lateinit var loginFailure: MutableLiveData<String>
    private val aplication = application

    fun getLoginResponseObservable(): LiveData<LoginResponse> {
        if (!::loginResponse.isInitialized) {
            loginResponse = MutableLiveData()
        }

        return loginResponse
    }

    fun getLoginFailureObservable(): LiveData<String> {
        if (!::loginFailure.isInitialized) {
            loginFailure = MutableLiveData()
        }

        return loginFailure
    }

    fun login(loginRequest: LoginRequest) {
        val callLogin = repository.login(loginRequest)

        callLogin.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, throwable: Throwable) {
                Log.d("CallApi", "Error: ${throwable.message}")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    loginResponse.value = response.body()
                } else {
                    when(response.code()) {
                        400 -> loginFailure.value = aplication.getString(R.string.login_failure)
                    }
                }
            }
        })
    }
}