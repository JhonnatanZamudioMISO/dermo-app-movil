package com.miso.dermoapp.data.attributes.typeKin.repository

import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.typeKin.repository
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:18 a. m.
 * All rights reserved 2023.
 ****/

interface TypeKinRepositoryInterface {
    suspend fun getDataTypeKin():List<ResponseKinType>
}