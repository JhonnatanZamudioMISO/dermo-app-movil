package com.miso.dermoapp.data.attributes.typeKin.repository

import com.miso.dermoapp.data.attributes.typeKin.datasource.TypeKinDataSourceLocal
import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.typeKin.repository
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:19 a. m.
 * All rights reserved 2023.
 ****/

class TypeKinRepository(private val typeKinDataSourceLocal: TypeKinDataSourceLocal) :
    TypeKinRepositoryInterface {
    companion object{
        @Volatile
        private var instance: TypeKinRepository? = null
        fun getInstance(            typeKinDataSourceLocal: TypeKinDataSourceLocal): TypeKinRepository =
            instance ?: synchronized(this) {
                instance ?: TypeKinRepository(typeKinDataSourceLocal)
            }
    }

    override suspend fun getDataTypeKin():List<ResponseKinType>{
        return typeKinDataSourceLocal.getDataTypeDocument()
    }
}