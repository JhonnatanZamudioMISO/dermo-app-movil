package com.miso.dermoapp.domain.useCases

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miso.dermoapp.BuildConfig
import com.miso.dermoapp.data.attributes.version.datasource.VersionDataSourceLocal
import com.miso.dermoapp.data.attributes.version.entitie.Version
import com.miso.dermoapp.data.attributes.version.repository.VersionRepository
import com.miso.dermoapp.data.room.DermoAppDB
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

/****
 * Project: DermoApp
 * From: com.miso.dermoapp.domain.useCases
 * Created by Jhonnatan E. Zamudio P. on 29/01/2023 at 7:40 p. m.
 * All rights reserved 2023.
 */
@RunWith(AndroidJUnit4::class)
@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@Config(sdk = [30])
class SplashScreenUseCaseTest {
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private var context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var database: DermoAppDB
    private lateinit var versionDataSourceLocal: VersionDataSourceLocal
    private lateinit var versionRepository: VersionRepository
    private lateinit var splashUseCase: SplashUseCase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context,
            DermoAppDB::class.java
        ).allowMainThreadQueries().build()
        versionDataSourceLocal = VersionDataSourceLocal.getInstance(database.versionDAO())
        versionRepository = VersionRepository.getInstance(versionDataSourceLocal)
        splashUseCase = SplashUseCase(versionRepository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        database.close()
    }

    @Test
    fun `Caso 01`(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            val result = splashUseCase.getAppVersion()
            assertEquals("Versión " + BuildConfig.VERSION_NAME, result)
        }
    }

    @Test
    fun `Caso 2`(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            versionRepository.insertVersionLocal(Version(0,1,"1.0.0", Calendar.getInstance().time))
            versionRepository.insertVersionLocal(Version(0,2,"1.0.1",Calendar.getInstance().time))
            val result = splashUseCase.getAppVersion()
            versionRepository.clearVersionsLocal()
            assertEquals("Versión 1.0.1" ,result)
        }
    }

    @Test
    fun `Caso 3`(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            versionRepository.insertVersionLocal(Version(0,1,"1.0.0",Calendar.getInstance().time))
            val result = splashUseCase.getAppVersion()
            versionRepository.clearVersionsLocal()
            assertEquals("Versión " + BuildConfig.VERSION_NAME ,result)
        }
    }


}