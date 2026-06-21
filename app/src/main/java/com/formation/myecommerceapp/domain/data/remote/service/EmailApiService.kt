package com.formation.myecommerceapp.domain.data.remote.service

import com.formation.myecommerceapp.domain.data.remote.dto.EmailRequest
import com.formation.myecommerceapp.domain.data.remote.dto.EmailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface EmailApiService {
    @POST("emails")
    suspend fun sendEmail(
        @Header("Authorization") apiKey: String,
        @Body request: EmailRequest
    ): Response<EmailResponse>
}
