package com.miso.dermoapp.data.attributes.city.entitie

import com.google.gson.annotations.SerializedName

/****
    * Project: DermoApp
    * From: com.miso.dermoapp.data.attributes.city.entitie
    * Created by Jhonnatan E. Zamudio P. on 25/02/2023 at 12:52 p. m.
    * All rights reserved 2023.
****/

data class ResponseCities(
    @SerializedName("ciudades") val data: List<String>,
    @SerializedName("pais") val pais: String
)
