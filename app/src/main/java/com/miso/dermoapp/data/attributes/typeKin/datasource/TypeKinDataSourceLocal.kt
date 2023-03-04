package com.miso.dermoapp.data.attributes.typeKin.datasource

import com.miso.dermoapp.data.attributes.typeKin.entitie.ResponseKinType
import com.miso.dermoapp.data.attributes.typeKin.source.TypeKinJSON

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.data.attributes.typeKin.datasource
 * Created by Jhonnatan E. Zamudio P. on 2/03/2023 at 5:20 a. m.
 * All rights reserved 2023.
 ****/

class TypeKinDataSourceLocal(private val typeKinJSON: TypeKinJSON) {

    companion object {
        private var INSTANCE: TypeKinDataSourceLocal? = null
        fun getInstance(typeKinJSON: TypeKinJSON): TypeKinDataSourceLocal =
            INSTANCE ?: TypeKinDataSourceLocal(typeKinJSON)
    }

    fun getDataTypeDocument(): List<ResponseKinType> {
        return typeKinJSON.getDataTypeDocument()
    }

}
