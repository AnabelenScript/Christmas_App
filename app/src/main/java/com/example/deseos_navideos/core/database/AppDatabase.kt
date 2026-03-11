package com.example.deseos_navideos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deseos_navideos.core.database.daos.KidsDao
import com.example.deseos_navideos.core.database.daos.UserDao
import com.example.deseos_navideos.core.database.daos.WishDao
import com.example.deseos_navideos.core.database.entities.KidEntity
import com.example.deseos_navideos.core.database.entities.UserEntity
import com.example.deseos_navideos.core.database.entities.WishEntity

@Database(
    entities = [
        WishEntity::class,
        KidEntity::class,
        UserEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wishDao(): WishDao

    abstract fun kidsDao(): KidsDao

    abstract fun userDao(): UserDao
}