package com.miso.dermoapp.ui.core.injury.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityPhotoOfInjuryBinding
import com.miso.dermoapp.domain.models.enumerations.CodeResponseLoginUser
import com.miso.dermoapp.domain.models.enumerations.CodeSnackBarCloseAction
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.enumerations.TypeSnackBar
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import com.miso.dermoapp.ui.core.injury.viewModels.PhotoOfInjuryViewModel
import com.miso.dermoapp.ui.core.injury.viewModels.PhotoOfInjuryViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSnackBar
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION", "COMPATIBILITY_WARNING")
class PhotoOfInjury : AppCompatActivity() {
    private lateinit var viewModel: PhotoOfInjuryViewModel
    private lateinit var binding: ActivityPhotoOfInjuryBinding
    private lateinit var loadingDialog: LoadingDialog
    var currentPhotoPath: String? = null
    val REQUEST_TAKE_PHOTO = 1
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = PhotoOfInjuryViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, viewModelFactory)[PhotoOfInjuryViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_of_injury)
        binding.lifecycleOwner = this
        binding.vModel = viewModel
        loadingDialog = LoadingDialog(this, getString(R.string.creando_lesion_dermatologica))

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.statusPhoto.observe(this) {
            if (it)
                TakePhoto()
        }

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

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonContinue.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonContinue.isEnabled = it
        })

        viewModel.navigateToInjury.observe(this, {
            if (it) {
                loadingDialog.startLoadingDialog()
                if (viewModel.checkOnline(this))
                    viewModel.createInjury()
                else
                    viewModel.snackBarAction.value = 0
            }
        })

        viewModel.resultCreateInjury.observe(this, {
            when(it){
                CodeResponseLoginUser.CREAR_LESION.code-> loadingDialog.succesful(R.string.creada_lesion_exito)
                CodeResponseLoginUser.ERROR.code -> loadingDialog.error()
            }
            viewModel.delayScreen(it)
        })

        viewModel.validateChangeScreen.observe(this, {
            when(it) {
                CodeResponseLoginUser.CREAR_LESION.code -> {
                    goToScreen(Intent(this@PhotoOfInjury, Injuries::class.java))
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
            val imgFile = File(sharedPreferences().get(this, KeySharedPreferences.PATH_PHOTO_OF_LESION.value))
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            binding.imageViewPhoto.setBackgroundResource(R.drawable.ic_eye)
            val bitmapAjusted = redimensionarImagen(myBitmap, 1080f, sharedPreferences().get(this, KeySharedPreferences.PATH_PHOTO_OF_LESION.value))!!
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
        sharedPreferences().set(this, KeySharedPreferences.PATH_PHOTO_OF_LESION.value, currentPhotoPath.toString())
        return image
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        loadingDialog.closeTypeOfInjury(getResources().getString(R.string.importante), getResources().getString(R.string.messageCloseType),
            CodeResponseLoginUser.ERROR.code)
    }
}