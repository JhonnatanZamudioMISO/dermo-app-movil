package com.miso.dermoapp.ui.core.profile.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserDematologicalProfileBinding
import com.miso.dermoapp.domain.models.enumerations.CodeTypeSpinner
import com.miso.dermoapp.domain.models.enumerations.KeySharedPreferences
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.domain.models.utils.sharedPreferences
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModel
import com.miso.dermoapp.ui.core.profile.viewModels.UserDermatologicalProfileViewModelFactory
import com.miso.dermoapp.ui.core.utils.CustomSpinnerAdapter
import com.miso.dermoapp.ui.core.utils.ListDialog
import com.miso.dermoapp.ui.core.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
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
            if (it != null)
                binding.buttonTypeOfKin.text =
                    getString(R.string.espacio) + viewModel.typeKinsList[it].abbreviate + getString(R.string.separador) + viewModel.typeKinsList[it].description
        }

        viewModel.statusPhoto.observe(this) {
            if (it)
                TakePhoto()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val imgFile = File(sharedPreferences().get(this, KeySharedPreferences.PATH_TIPO_PIEL.value))
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            binding.imageViewPhoto.setBackgroundResource(R.drawable.ic_eye)
            binding.imageViewPhoto.setImageBitmap(myBitmap)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun TakePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    println("SE creo un error")
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