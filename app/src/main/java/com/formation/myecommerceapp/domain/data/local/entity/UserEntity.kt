package com.formation.myecommerceapp.domain.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String, // L'email est identifiant unique
    val password: String
)