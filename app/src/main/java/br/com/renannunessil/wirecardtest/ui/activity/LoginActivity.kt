package br.com.renannunessil.wirecardtest.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.renannunessil.wirecardtest.R
import br.com.renannunessil.wirecardtest.data.model.LoginRequest
import br.com.renannunessil.wirecardtest.data.model.User
import br.com.renannunessil.wirecardtest.viewmodel.LoginViewModel
import br.com.renannunessil.wirecardtest.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var password: String

    private val TOKEN = "token"
    private val client_id = "APP-H1DR0RPHV7SP"
    private val USER = "user"
    private val clientSecret = "05acb6e128bc48b2999582cd9a2b9787"
    private val grantType = "password"
    private val scope = "APP_ADMIN"
    private val deviceId = "1"
    private lateinit var user: User

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]
        userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]
        setupUi()
    }

    private fun callLogin(username: String, password: String) {
        user = User(username, password, "")
        val loginRequest = LoginRequest(client_id, clientSecret, grantType, username, password, deviceId, scope)

        if (validateInputs(username, password)) {
            showLoading(true)
            loginViewModel.login(loginRequest)
        } else {
            makeToast(application.getString(R.string.empty_inputs))
        }
    }

    private fun subscribe() {
        loginViewModel.getLoginResponseObservable().observe(this, Observer {
            showLoading(false)
            if (it != null) {
                onLoginSuccess(it.token)
            }
        })

        loginViewModel.getLoginFailureObservable().observe(this, Observer {
            showLoading(false)
            makeToast(it)
        })
    }

    private fun validateInputs(vararg inputs: String): Boolean {
        var isValid = false
        for (input in inputs) {
            if (!input.isEmpty()) {
                isValid = true
            } else {
                return false
            }
        }

        return isValid
    }

    private fun setOnClickListeners() {
        bt_login.setOnClickListener {
            username = et_user.text.toString()
            password = et_password.text.toString()
            callLogin(username, password)
        }
    }

    private fun onLoginSuccess(token: String) {
        user.token = token
        val intent = Intent(this, OrdersActivity::class.java)
        intent.putExtra(TOKEN, token)
        intent.putExtra(USER, user)
        startActivity(intent)
    }

    private fun setupUi() {
        subscribe()
        setOnClickListeners()
        autoLogin()
    }

    private fun showLoading(show: Boolean) {
        when(show) {
            true -> cl_progressbar.visibility = View.VISIBLE
            else -> cl_progressbar.visibility = View.GONE
        }
    }

    private fun makeToast(message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun autoLogin() {
        val user = userViewModel.getUser()
        if (user != null) {
            if (!user.login.isEmpty() && !user.password.isEmpty() && !user.token.isEmpty()) {
                this.user = user
                onLoginSuccess(user.token)
            }
        }
    }
}
