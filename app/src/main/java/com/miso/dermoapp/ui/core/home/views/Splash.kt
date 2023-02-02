package com.miso.dermoapp.ui.core.home.views

import android.Manifest
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivitySplashBinding
import com.miso.dermoapp.domain.models.enumerations.CodePermissions
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModel
import com.miso.dermoapp.ui.core.home.viewModels.SplashViewModelFactory
import kotlinx.coroutines.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class Splash : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DermoApp)
        super.onCreate(savedInstanceState)
        val viewModelFactory = SplashViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.vModel = viewModel

        viewModel.loading.observe(this, {
                with(binding) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        if (it.equals(true)) {
                            startLoading(imageViewLoading)
                        } else {
                            stopLoading(imageViewLoading)
                        }
                    }
                }
            },
        )

        viewModel.validatePermissions.observe(this, {
            if(it.equals(true)){
                validatePermission(R.string.rationale_write_storage, CodePermissions.WRITE_STORAGE.code,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        })
    }

    private fun validatePermission(message: Int, code: Int, permission: String) {
        when (hasPermission(permission)) {
            true -> {
                when (code) {
                    CodePermissions.CAMERA.code ->  viewModel.loading.value = false
                    CodePermissions.WRITE_STORAGE.code -> validatePermission(R.string.rationale_camera,
                        CodePermissions.CAMERA.code, Manifest.permission.CAMERA)
                }
            }
            false -> EasyPermissions.requestPermissions(this, getString(message), code, permission)
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return EasyPermissions.hasPermissions(this, permission)
    }
    private fun stopLoading(imageViewLoading: ImageView) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.invisible)
        imageViewLoading.startAnimation(animation)
    }

    private fun startLoading(imageViewLoading: ImageView) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.loading)
        imageViewLoading.startAnimation(animation)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,
            this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
        when (requestCode) {
            CodePermissions.WRITE_STORAGE.code -> validatePermission(R.string.rationale_camera,
                CodePermissions.CAMERA.code, Manifest.permission.CAMERA)
            CodePermissions.CAMERA.code -> viewModel.loading.value = false
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
        viewModel.loading.value = false
        Toast.makeText(this, R.string.permisos_denegados,
            Toast.LENGTH_LONG).show()
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}