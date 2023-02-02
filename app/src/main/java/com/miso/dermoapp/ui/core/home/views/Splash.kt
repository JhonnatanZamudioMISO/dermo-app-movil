package com.miso.dermoapp.ui.core.home.views

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySplashBinding
import com.miso.dermoapp.domain.models.enumerations.CodePermissions
import com.miso.dermoapp.domain.models.enumerations.TypeSnackBar
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
import kotlinx.coroutines.*
import pub.devrel.easypermissions.*

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class Splash : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    private var appUpdateManager: AppUpdateManager? = null
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
            if (it.equals(true))
                viewModel.checkUpdate(appUpdateManager!!)
            else {
                viewModel.loading.postValue(false)
                viewModel.snackBarTextCloseApp.postValue(resources.getString(R.string.sin_conexion))
            }
        })
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
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
        when (requestCode) {
            CodePermissions.WRITE_STORAGE.code -> viewModel.hasPermission(
                this,
                Manifest.permission.CAMERA
            )
            CodePermissions.CAMERA.code -> viewModel.checkOnline(this)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
        viewModel.loading.postValue(false)
        viewModel.snackBarTextCloseApp.postValue(resources.getString(R.string.permisos_denegados))
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            AppSettingsDialog.Builder(this).build().show()
    }
}