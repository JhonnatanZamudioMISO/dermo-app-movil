package com.miso.dermoapp.domain.models.enumerations

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.enumerations
 * Created by Jhonnatan E. Zamudio P. on 2/02/2023 at 10:11 p. m.
 * All rights reserved 2023.
 ****/

enum class KeySharedPreferences(val value: String) {
    IDIOMA("idioma"),
    ENGLISH("ingles"),
    SPANISH("español"),
    STATUS_PROFILE("perfil"),
    NAME("nombre"),
    AGE("edad"),
    CITY("ciudad"),
    EMAIL ("email"),
    PATH_TIPO_PIEL("tipo_de_piel"),
    TYPE_OF_INJURY("tipo_de_lesion"),
    FORM_OF_THE_INJURY("forma_de_la_lesion"),
    NUMBER_OF_INJURIES("numero_de_lesiones"),
    DISTRIBUTION("distribucion")
}