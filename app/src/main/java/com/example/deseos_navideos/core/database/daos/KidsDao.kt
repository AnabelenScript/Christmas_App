package com.example.deseos_navideos.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deseos_navideos.core.database.entities.KidEntity

@Dao
interface KidsDao {

    @Query("SELECT * FROM kids")
    suspend fun getKids(): List<KidEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKids(kids: List<KidEntity>)

    @Query("DELETE FROM kids")
    suspend fun clear()
}