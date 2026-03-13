package com.example.deseos_navideos.core.di

import android.content.Context
import androidx.room.Room
import com.example.deseos_navideos.core.database.AppDatabase
import com.example.deseos_navideos.core.database.daos.KidsDao
import com.example.deseos_navideos.core.database.daos.UserDao
import com.example.deseos_navideos.core.database.daos.WishDao
import com.example.deseos_navideos.core.database.migrations.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "wishes_db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideWishDao(db: AppDatabase): WishDao {
        return db.wishDao()
    }

    @Provides
    fun provideKidsDao(db: AppDatabase): KidsDao {
        return db.kidsDao()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }
}