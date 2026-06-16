package com.formation.myecommerceapp.domain.di

import android.content.Context
import androidx.room.Room
import com.formation.myecommerceapp.domain.data.local.AppDatabase
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.data.repository.ProductLocalFirstRepository
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
import org.koin.dsl.module
import org.koin.plugin.module.dsl.create

fun createAppDatabase(applicationContext: Context): AppDatabase =
    Room.databaseBuilder(
        context = applicationContext,
        klass = AppDatabase::class.java,
        name = "my-ecommerce-app-db"
    ).build()

fun createProductDao(database: AppDatabase): ProductDao =
    database.getProductDao()

val databaseModule = module {
    single { createAppDatabase(get()) }
    single { createProductDao(get()) }
    single<ProductRepository> { ProductLocalFirstRepository(get(), get()) }
}