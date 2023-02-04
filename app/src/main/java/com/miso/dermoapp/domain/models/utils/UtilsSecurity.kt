package com.miso.dermoapp.domain.models.utils

import android.annotation.SuppressLint
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.models.utils
 * Created by Jhonnatan E. Zamudio P. on 4/02/2023 at 8:58 a. m.
 * All rights reserved 2023.
 ****/

class UtilsSecurity {

    private val password = "xMAcAfTt5EB3iuJB"

    @SuppressLint("GetInstance")
    @Throws(Exception::class)
    fun cipherData(data: String): String? {
        val secretKeySpec: SecretKeySpec = generateKey(password)
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val datoEncriptado: ByteArray = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(datoEncriptado, Base64.DEFAULT)
    }

    @Throws(java.lang.Exception::class)
    private fun generateKey(clave: String): SecretKeySpec {
        val sha: MessageDigest = MessageDigest.getInstance("SHA-256")
        var key = clave.toByteArray(charset("UTF-8"))
        key = sha.digest(key)
        return SecretKeySpec(key, "AES")
    }

    @SuppressLint("GetInstance")
    @Throws(java.lang.Exception::class)
    private fun decipherData(data: String): String {
        val secretKeySpec = generateKey(password)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val datoDescifrado =
            Base64.decode(data, Base64.DEFAULT)
        val datoDescifradoByte = cipher.doFinal(datoDescifrado)
        return String(datoDescifradoByte)
    }
}