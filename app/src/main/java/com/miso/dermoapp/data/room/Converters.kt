package com.miso.dermoapp.data.room

import androidx.room.TypeConverter
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.room
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 1:35 p. m.
 * All rights reserved 2023.
 ****/

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}