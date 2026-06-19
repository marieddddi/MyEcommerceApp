package com.formation.myecommerceapp.domain.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import com.formation.myecommerceapp.domain.data.local.dao.UserDao
import com.formation.myecommerceapp.domain.data.local.entity.UserEntity

@Database(
    entities = [ProductEntity::class, UserEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
}