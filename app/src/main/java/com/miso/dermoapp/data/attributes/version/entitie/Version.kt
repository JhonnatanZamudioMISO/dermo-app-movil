package com.miso.dermoapp.data.attributes.version.entitie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.version.entitie
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 8:12 p. m.
 * All rights reserved 2023.
 ****/

@Parcelize
@Entity
data class Version (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var versionCode: Int,
    var versionName: String,
    var versionDate: Date
): Parcelable