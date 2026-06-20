package com.formation.myecommerceapp.domain.di

import android.content.Context
import androidx.room.Room
import com.formation.myecommerceapp.domain.data.local.AppDatabase
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.data.local.dao.UserDao // 👈 AJOUTE CET IMPORT
import com.formation.myecommerceapp.domain.data.repository.ProductLocalFirstRepository
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
import org.koin.dsl.bind
import org.koin.dsl.module

fun createAppDatabase(applicationContext: Context): AppDatabase =
    Room.databaseBuilder(
        context = applicationContext,
        klass = AppDatabase::class.java,
        name = "my-ecommerce-app-db"
    ).fallbackToDestructiveMigration(dropAllTables = true)
        .build()

fun createProductDao(database: AppDatabase): ProductDao =
    database.productDao()
fun createUserDao(database: AppDatabase): UserDao =
    database.userDao()

val databaseModule = module {
    single { createAppDatabase(get()) }
    single { createProductDao(get()) }
    single { createUserDao(get()) }
    single<ProductRepository> { ProductLocalFirstRepository(get(), get()) }
    single { ProductLocalFirstRepository(get(), get()) } bind ProductRepository::class
}