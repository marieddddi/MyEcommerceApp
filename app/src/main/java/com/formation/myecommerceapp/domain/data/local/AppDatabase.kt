package com.formation.myecommerceapp.domain.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao
}