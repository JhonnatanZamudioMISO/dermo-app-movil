package com.miso.dermoapp.data.attributes.profileDermatological.repository

import com.miso.dermoapp.data.attributes.profileDermatological.entitie.RequestProfileDermatological
import com.miso.dermoapp.data.attributes.profileDermatological.entitie.ResponseProfileDermatological

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.profileDermatological.repository
 * Created by Jhonnatan E. Zamudio P. on 4/03/2023 at 8:05 a. m.
 * All rights reserved 2023.
 ****/

interface ProfileDermatologicalRepositoryInterface {
    suspend fun insertCreateProfileRemote(requestProfile: RequestProfileDermatological): ResponseProfileDermatological
}