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
import com.miso.dermoapp.domain.models.enumerations.CodePermissions
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
import android.Manifest
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.miso.dermoapp.R
import org.mockito.Mockito

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
    private val permissionWriteStorge = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val permissionCamera = Manifest.permission.CAMERA
    private val permissionInternet = Manifest.permission.INTERNET
    val fakeAppUpdateManager by lazy { Mockito.spy(FakeAppUpdateManager(context)) }

    private suspend fun createVersions(i: Int) {
        for (x in 1..i) {
            versionRepository.insertVersionLocal(
                Version(
                    0,
                    x,
                    "1.0.$x",
                    Calendar.getInstance().time
                )
            )
        }
    }

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
            createVersions(4)
            val result = splashUseCase.getAppVersion()
            assertEquals("Versión " + BuildConfig.VERSION_NAME ,result)
        }
    }

    @Test
    fun `Caso 3`(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            createVersions(1)
            val result = splashUseCase.getAppVersion()
            assertEquals("Versión " + BuildConfig.VERSION_NAME ,result)
        }
    }

    @Test
    fun `Caso 4`() {
        val result = splashUseCase.getCodePermission(permissionWriteStorge)
        assertEquals(CodePermissions.WRITE_STORAGE.code,result)
    }

    @Test
    fun `Caso 5`() {
        val result = splashUseCase.getCodePermission(permissionCamera)
        assertEquals(CodePermissions.CAMERA.code,result)
    }

    @Test
    fun `Caso 6`() {
        val result = splashUseCase.getMessagePermission(permissionWriteStorge, context)
        assertEquals(context.getString(R.string.rationale_write_storage),result)
    }

    @Test
    fun `Caso 7`() {
        val result = splashUseCase.getMessagePermission(permissionCamera, context)
        assertEquals(context.getString(R.string.rationale_camera),result)
    }

    @Test
    fun `Caso 8`() {
        val result = splashUseCase.getMessagePermission(permissionInternet, context)
        assertEquals(context.getString(R.string.rationale_default),result)
    }

    @Test
    fun `Caso 9`() {
        fakeAppUpdateManager.setUpdateAvailable(UpdateAvailability.UPDATE_AVAILABLE, AppUpdateType.IMMEDIATE)
        val appUpdateInfoTask = fakeAppUpdateManager!!.appUpdateInfo
        val result = splashUseCase.shouldBeUpdated(appUpdateInfoTask.result)
        assertEquals(true,result)
    }

    @Test
    fun `Caso 10`() {
        fakeAppUpdateManager.downloadStarts()
        fakeAppUpdateManager.setUpdateAvailable(UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
        val appUpdateInfoTask = fakeAppUpdateManager!!.appUpdateInfo
        val result = splashUseCase.shouldBeUpdated(appUpdateInfoTask.result)
        assertEquals(true,result)
    }

    @Test
    fun `Caso 11`() {
        val result = splashUseCase.getDefaultLanguage(context)
        assertEquals(0,result)
    }
}