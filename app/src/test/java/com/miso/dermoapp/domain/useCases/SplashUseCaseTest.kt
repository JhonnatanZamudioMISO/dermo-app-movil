package com.miso.dermoapp.domain.useCases

import com.miso.dermoapp.BuildConfig
import org.junit.Test
import org.junit.Assert.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 7:40 p. m.
 * All rights reserved 2023.
 */

class SplashScreenUseCaseTest {
    val splashUseCase = SplashUseCase()

    @Test
    fun `Caso 01`() {
        val result = splashUseCase.getAppVersion()
        assertEquals("Versión " + BuildConfig.VERSION_NAME,result)
    }
}