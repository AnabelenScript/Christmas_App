package com.example.deseos_navideos.core.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 1. Crear la nueva tabla con la estructura correcta
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `wishes_new` (" +
                    "`localId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`id` INTEGER NOT NULL, " +
                    "`wish` TEXT NOT NULL, " +
                    "`idUser` INTEGER NOT NULL, " +
                    "`username` TEXT, " +
                    "`state` TEXT NOT NULL, " +
                    "`photoUrl` TEXT, " +
                    "`syncState` TEXT NOT NULL, " +
                    "`localFilePath` TEXT, " +
                    "`taskType` TEXT NOT NULL, " +
                    "`role` TEXT)"
        )

        // 2. Copiar los registros de la tabla anterior a la nueva.
        // Asignamos el valor por defecto 'WISH' a taskType para los registros existentes.
        database.execSQL(
            "INSERT INTO `wishes_new` (`id`, `wish`, `idUser`, `username`, `state`, `photoUrl`, `syncState`, `taskType`) " +
                    "SELECT `id`, `wish`, `idUser`, `username`, `state`, `photoUrl`, `syncState`, 'WISH' FROM `wishes`"
        )

        // 3. Eliminar la tabla anterior
        database.execSQL("DROP TABLE `wishes`")

        // 4. Renombrar la tabla nueva para que tome el lugar de la anterior
        database.execSQL("ALTER TABLE `wishes_new` RENAME TO `wishes`")
    }
}