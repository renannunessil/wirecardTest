package br.com.renannunessil.wirecardtest.data.model

class LoginRequest(val clientId: String,
                   val clientSecret: String,
                   val grantType: String,
                   val username: String,
                   val password: String,
                   val deviceId: String,
                   val scope: String)