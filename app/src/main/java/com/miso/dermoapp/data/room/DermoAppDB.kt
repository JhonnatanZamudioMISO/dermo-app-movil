package com.miso.dermoapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miso.dermoapp.data.attributes.version.entitie.Version
import com.miso.dermoapp.data.attributes.version.source.VersionDAO

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.room
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 1:35 p. m.
 * All rights reserved 2023.
 ****/

@Database(entities = [Version::class], version = 1)
@TypeConverters(Converters::class)
abstract class DermoAppDB : RoomDatabase() {

    abstract fun versionDAO(): VersionDAO

    companion object {
        @Volatile
        private var INSTANCE: DermoAppDB? = null

        fun getInstance(context: Context): DermoAppDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DermoAppDB::class.java,
                        "dermoapp_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}