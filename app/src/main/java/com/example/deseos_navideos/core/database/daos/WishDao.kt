package com.example.deseos_navideos.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deseos_navideos.core.database.entities.WishEntity

@Dao
interface WishDao {

    @Query("SELECT * FROM wishes WHERE idUser = :userId AND taskType = 'WISH'")
    suspend fun getWishesByUser(userId: Int): List<WishEntity>

    @Query("SELECT * FROM wishes WHERE taskType = 'WISH'")
    suspend fun getAll(): List<WishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWish(wish: WishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wishes: List<WishEntity>)

    @Query("SELECT * FROM wishes WHERE syncState = 'PENDING'")
    suspend fun getPendingTasks(): List<WishEntity>

    @Query("UPDATE wishes SET syncState = 'SYNCED', id = :remoteId WHERE localId = :localId")
    suspend fun markAsSynced(localId: Int, remoteId: Int)

    @Query("DELETE FROM wishes WHERE localId = :localId")
    suspend fun deleteByLocalId(localId: Int)

    @Query("DELETE FROM wishes WHERE idUser = :userId AND taskType = 'WISH'")
    suspend fun deleteWishesByUser(userId: Int)

    @Query("DELETE FROM wishes WHERE idUser != :currentUserId AND syncState = 'SYNCED' AND taskType = 'WISH'")
    suspend fun deleteOtherSyncedWishes(currentUserId: Int)

    @Query("DELETE FROM wishes")
    suspend fun clear()
}