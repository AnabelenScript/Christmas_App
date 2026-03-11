package com.example.deseos_navideos.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deseos_navideos.core.database.entities.WishEntity

@Dao
interface WishDao {

    @Query("SELECT * FROM wishes WHERE idUser = :userId")
    suspend fun getWishesByUser(userId: Int): List<WishEntity>

    @Query("SELECT * FROM wishes")
    suspend fun getAll(): List<WishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wishes: List<WishEntity>)

    @Query("DELETE FROM wishes")
    suspend fun clear()
}