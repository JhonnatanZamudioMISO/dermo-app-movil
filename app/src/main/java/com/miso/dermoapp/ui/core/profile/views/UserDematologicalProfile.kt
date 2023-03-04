package com.miso.dermoapp.ui.core.profile.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserDematologicalProfileBinding
import com.miso.dermoapp.domain.models.enumerations.*
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import com.miso.dermoapp.ui.core.dashboard.views.Dashboard
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModel
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
import com.miso.dermoapp.ui.core.utils.CustomSpinnerAdapter
import com.miso.dermoapp.ui.core.utils.ListDialog
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION", "COMPATIBILITY_WARNING")
class UserDematologicalProfile : AppCompatActivity() {
    private lateinit var viewModel: UserDermatologicalProfileViewModel
    private lateinit var binding: ActivityUserDematologicalProfileBinding
    private lateinit var loadingDialog: LoadingDialog
    private val tag = "UserDermaologicalProfile"
    var currentPhotoPath: String? = null
    val REQUEST_TAKE_PHOTO = 1
    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResponseErrorField.initialize(applicationContext)
        val viewModelFactory = UserDermatologicalProfileViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserDermatologicalProfileViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_dematological_profile)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.configurandoPerfilDermatologico))

        binding.buttonTypeOfKin.setSelected(true)
        binding.buttonTypeOfKin.text = getString(R.string.espacio) +getString(R.string.seleccion_tipo_piel)

        binding.buttonTypeOfKin.setOnClickListener {
            createDialogSpinner(viewModel.typeKinsList, CodeTypeSpinner.TYPE_KIN.code)
        }

        viewModel.typeKinSelectedPosition.observe(this) {
            if (it != null) {
                binding.buttonTypeOfKin.text =
                    getString(R.string.espacio) + viewModel.typeKinsList[it].abbreviate + getString(
                        R.string.separador
                    ) + viewModel.typeKinsList[it].description
                viewModel.validateSpinner(viewModel.typeKinsList[it].abbreviate + getString(
                    R.string.separador
                ) + viewModel.typeKinsList[it].description)
            }
        }

        viewModel.statusPhoto.observe(this) {
            if (it)
                TakePhoto()
        }

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonContinue.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonContinue.isEnabled = it
        })

        viewModel.navigateToDashboard.observe(this, {
            if (it) {
                loadingDialog.startLoadingDialog()
                if (viewModel.checkOnline(this))
                    viewModel.createProfileDermatological()
                else
                    viewModel.snackBarAction.value = 0
            }
        })

        viewModel.snackBarAction.observe(this, {
            loadingDialog.hideLoadingDialog()
            when (it){
                0 -> viewModel.snackBarTextWarning.postValue(getString(R.string.sin_conexion))
                1 -> viewModel.snackBarTextWarning.postValue(getString(R.string.problemas_almacenamiento_imagen))
            }
            viewModel.snackBarNavigate.postValue(CodeSnackBarCloseAction.NONE.code)
        })

        viewModel.snackBarTextWarning.observe(this, {
            CustomSnackBar().showSnackBar(
                it,
                binding.constraintLayout,
                TypeSnackBar.WARNING.code,
                this,
                viewModel.snackBarNavigate.value!!
            )
        })

        viewModel.resultCreateProfileDermatological.observe(this, {
            when(it){
                CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code-> loadingDialog.succesful(R.string.agregadoConExito)
                CodeResponseLoginUser.ERROR.code -> loadingDialog.error()
            }
            viewModel.delayScreen(it)
        })

        viewModel.validateChangeScreen.observe(this, {
            when(it) {
                CodeResponseLoginUser.PERFIL_DERMATOLOGICO.code -> {
                    goToScreen(Intent(
                            this@UserDematologicalProfile,
                            Dashboard::class.java
                        )
                    )
                }
                CodeResponseLoginUser.ERROR.code -> loadingDialog.hideLoadingDialog()
            }
        })
    }
    private fun goToScreen(intent: Intent) {
        loadingDialog.hideLoadingDialog()
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        finish()
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val imgFile = File(sharedPreferences().get(this, KeySharedPreferences.PATH_TIPO_PIEL.value))
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            binding.imageViewPhoto.setBackgroundResource(R.drawable.ic_eye)
            val bitmapAjusted = redimensionarImagen(myBitmap, 1280f, sharedPreferences().get(this, KeySharedPreferences.PATH_TIPO_PIEL.value))!!
            binding.imageViewPhoto.setImageBitmap(bitmapAjusted)
            viewModel.validatePhoto(bitmapAjusted)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Throws(IOException::class)
    fun redimensionarImagen(bitmap: Bitmap, anchoNuevo: Float, photoFile: String): Bitmap? {
        //leemos los parametro de la foto
        val ancho = bitmap.width
        val alto = bitmap.height
        //calculamos la escala de conversion
        val escala = anchoNuevo / ancho
        val exif = ExifInterface(photoFile)
        val rotation: Int =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        val rotationInDegrees: Int = exifToDegrees(rotation)
        //Creamos la nueva matrix
        val matrix = Matrix()
        matrix.postScale(escala, escala)
        if (rotation.toFloat() != 0f) {
            matrix.preRotate(rotationInDegrees.toFloat())
        }
        //devolvemos
        return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false)
    }

    private fun exifToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun TakePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    viewModel.snackBarAction.value = 1
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        "com.miso.dermoapp.provider",
                        photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        @SuppressLint("SimpleDateFormat")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPhotoPath = image.absolutePath
        sharedPreferences().set(this, KeySharedPreferences.PATH_TIPO_PIEL.value, currentPhotoPath.toString())
        return image
    }

    private fun createDialogSpinner(dataList: List<Any>, code: Int) {
        val dialog = ListDialog(
            dataList,
            code,
            object : CustomSpinnerAdapter.CustomActionSpinner {
                override fun onItemSelected(position: Int) {
                    when (code) {
                        CodeTypeSpinner.TYPE_KIN.code -> viewModel.typeKinSelectedPosition.value =
                            position
                    }
                }
            })
        dialog.show(this.supportFragmentManager, tag)
    }
}