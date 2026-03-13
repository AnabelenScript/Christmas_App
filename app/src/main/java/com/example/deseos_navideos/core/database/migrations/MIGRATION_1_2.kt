package com.example.deseos_navideos.core.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE wishes ADD COLUMN syncState TEXT NOT NULL DEFAULT 'PENDING'")
    }
}