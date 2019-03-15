package br.com.renannunessil.wirecardtest.repository

import br.com.renannunessil.wirecardtest.data.model.LoginRequest
import br.com.renannunessil.wirecardtest.data.model.LoginResponse
import br.com.renannunessil.wirecardtest.data.remote.CallApi
import br.com.renannunessil.wirecardtest.data.remote.RetrofitClientInstance
import br.com.renannunessil.wirecardtest.utils.Constants
import retrofit2.Call

class LoginRepository {
    private val service: CallApi = RetrofitClientInstance.create(Constants.LOGIN_URL)

    fun login(loginRequest: LoginRequest): Call<LoginResponse> {
        return service.callLogin(loginRequest.clientId,
                                 loginRequest.clientSecret,
                                 loginRequest.grantType,
                                 loginRequest.username,
                                 loginRequest.password,
                                 loginRequest.deviceId,
                                 loginRequest.scope)
    }

    object LoginRepositoryProvider {
        fun provideLoginRepository(): LoginRepository {
            return LoginRepository()
        }
    }
}