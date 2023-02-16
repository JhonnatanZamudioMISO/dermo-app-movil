package com.miso.dermoapp.domain.useCases

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miso.dermoapp.data.attributes.user.datasource.UserDataSourceRemote
import com.miso.dermoapp.data.attributes.user.repository.UserRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 15/02/2023 at 11:12 p. m.
 * All rights reserved 2023.
 */
@RunWith(AndroidJUnit4::class)
@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@Config(sdk = [30])
class SignUpUseCaseTest {
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var userDataSourceRemote: UserDataSourceRemote
    private lateinit var userRepository: UserRepository
    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setup() {
        userDataSourceRemote = UserDataSourceRemote()
        userRepository = UserRepository.getInstance(userDataSourceRemote)
        signUpUseCase = SignUpUseCase(userRepository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `Caso 01`() {
        val result = signUpUseCase.changeEnableButton(0,0,0,false)
        Assert.assertEquals(false, result)
    }

    @Test
    fun `Caso 02`() {
        val result = signUpUseCase.changeEnableButton(0,0,0,true)
        Assert.assertEquals(false, result)
    }
}