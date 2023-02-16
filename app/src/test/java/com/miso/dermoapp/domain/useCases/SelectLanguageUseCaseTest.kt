package com.miso.dermoapp.domain.useCases

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miso.dermoapp.domain.models.enumerations.CodeSharedPreferences
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 15/02/2023 at 10:57 p. m.
 * All rights reserved 2023.
 */

@RunWith(AndroidJUnit4::class)
@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@Config(sdk = [30])
class SelectLanguageUseCaseTest {
    private lateinit var selectLanguageUseCase: SelectLanguageUseCase
    private var context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setup() {
        selectLanguageUseCase = SelectLanguageUseCase()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Caso 01`() {
        selectLanguageUseCase.saveDefaultLanguage(context, CodeSharedPreferences.SPANISH.code)
        Assert.assertEquals(CodeSharedPreferences.SPANISH.code, sharedPreferences().get(context,KeySharedPreferences.IDIOMA.value))
    }

    @Test
    fun `Caso 02`() {
        selectLanguageUseCase.saveDefaultLanguage(context, CodeSharedPreferences.ENGLISH.code)
        Assert.assertEquals(CodeSharedPreferences.ENGLISH.code, sharedPreferences().get(context,KeySharedPreferences.IDIOMA.value))
    }
}