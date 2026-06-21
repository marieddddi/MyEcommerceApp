package com.formation.myecommerceapp.domain.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmailRequest(
    val from: String,
    val to: String,
    val subject: String,
    val html: String
)

@Serializable
data class EmailResponse(
    val id: String
)
