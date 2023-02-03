package com.miso.dermoapp.ui.core.home.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySplashBinding
import com.miso.dermoapp.domain.models.enumerations.CodeActivityForResult
import com.miso.dermoapp.domain.models.enumerations.CodePermissions
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.enumerations.TypeSnackBar
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
import kotlinx.coroutines.*
import pub.devrel.easypermissions.*
import java.util.*

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class Splash : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    private var appUpdateManager: AppUpdateManager? = null
    private val config: Configuration = Configuration()
    private val TAG = "SplashScreen"
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DermoApp)
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val viewModelFactory = SplashViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.lifecycleOwner = this
        binding.vModel = viewModel

        viewModel.loading.observe(this, {
            lifecycleScope.launch(Dispatchers.IO) {
                animationLoading(binding.imageViewLoading, it)
            }
        })

        viewModel.validatePermissions.observe(this, {
            if (it.equals(true))
                viewModel.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        })

        viewModel.requestPermission.observe(this, {
            EasyPermissions.requestPermissions(
                this,
                viewModel.messagePermission.value!!,
                viewModel.codPermission.value!!,
                viewModel.typePermission.value
            )
        })

        viewModel.snackBarTextCloseApp.observe(this, {
            CustomSnackBar().showSnackBar(
                it,
                binding.layoutContain,
                TypeSnackBar.CLOSE_APP.code,
                this
            )
        })

        viewModel.isConected.observe(this, {
            if (it.equals(true)) {
                val appUpdateInfoTask = appUpdateManager!!.appUpdateInfo
                if (appUpdateInfoTask.isSuccessful)
                    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                        viewModel.checkUpdate(
                            appUpdateInfo
                        )
                    }
                else
                    validateLanguage()
            } else {
                viewModel.loading.postValue(false)
                viewModel.snackBarTextCloseApp.postValue(getString(R.string.sin_conexion))
            }
        })

        viewModel.startUpdateFlow.observe(this, {
            if (it.equals(true))
                starUpdateFlow()
            else
                validateLanguage()
        })
    }

    private fun validateLanguage() {
        viewModel.getDefaultLanguage(this)
        viewModel.configurationLanguage.observe(this,{
            when (it) {
                0 -> goToChangeScreen(0,Locale.getDefault().getDisplayLanguage())
                1 -> {
                    config.locale = Locale("en")
                    resources.updateConfiguration(config, null)
                    goToChangeScreen(1, null)
                }
                2 -> {
                    config.locale = Locale("es")
                    resources.updateConfiguration(config, null)
                    goToChangeScreen(1, null)
                }
            }
        })
    }

    private fun goToChangeScreen(screen: Int, value: String?) {
        var intent: Intent
        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                delay(500)
                viewModel.loading.postValue(false)
                when(screen){
                    0 -> {
                        intent = Intent(this@Splash, SelectLanguage::class.java)
                        intent.putExtra(KeySharedPreferences.IDIOMA.value, value)
                        startActivity(intent)
                    }
                    1 -> {
                        intent = Intent(this@Splash, Welcome::class.java)
                        startActivity(intent)
                    }
                }
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }
        }
    }

    private fun starUpdateFlow() {
        try {
            appUpdateManager!!.startUpdateFlowForResult(
                viewModel.appUpdateInfoPlayStore.value!!,
                AppUpdateType.IMMEDIATE,
                this,
                CodeActivityForResult.IMMEDIATE_APP_UPDATE_REQ_CODE.code
            )
        } catch (e: IntentSender.SendIntentException) {
            Log.e(TAG, "updateAppError:" + e.printStackTrace())
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CodeActivityForResult.IMMEDIATE_APP_UPDATE_REQ_CODE.code) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, getString(R.string.actualizacion_cancelada), Toast.LENGTH_LONG)
                    .show()
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(this, getString(R.string.actualizacion_exitosa), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.actualizacion_fallida), Toast.LENGTH_LONG)
                    .show()
                viewModel.checkOnline(this)
            }
        }
    }

    private fun animationLoading(imageViewLoading: ImageView, state: Boolean) {
        val animation = if (state)
            R.anim.loading
        else
            R.anim.invisible
        imageViewLoading.startAnimation(AnimationUtils.loadAnimation(this, animation))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(
            TAG,
            getString(R.string.on_permissions_granted) + requestCode + getString(R.string.double_point) + perms.size
        )
        when (requestCode) {
            CodePermissions.WRITE_STORAGE.code -> viewModel.hasPermission(
                this,
                Manifest.permission.CAMERA
            )
            CodePermissions.CAMERA.code -> viewModel.checkOnline(this)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(
            TAG,
            getString(R.string.permission_denied) + requestCode + getString(R.string.double_point) + perms.size
        )
        viewModel.loading.postValue(false)
        viewModel.snackBarTextCloseApp.postValue(getString(R.string.permisos_denegados))
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            AppSettingsDialog.Builder(this).build().show()
    }
}