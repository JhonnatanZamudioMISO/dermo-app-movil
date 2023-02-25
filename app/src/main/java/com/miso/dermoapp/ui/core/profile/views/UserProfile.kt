package com.miso.dermoapp.ui.core.profile.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserProfileBinding
import com.miso.dermoapp.domain.models.enumerations.ResponseErrorField
import com.miso.dermoapp.ui.core.profile.viewModels.UserProfileViewModel
import com.miso.dermoapp.ui.core.profile.viewModels.UserProfileViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("COMPATIBILITY_WARNING", "DEPRECATION")
class UserProfile : AppCompatActivity() {
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var binding: ActivityUserProfileBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResponseErrorField.initialize(applicationContext)
        val viewModelFactory = UserProfileViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserProfileViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        binding.lifecycleOwner = this
        binding.vModel = viewModel

        viewModel.citiesList.observe(this, {
                citiesList -> binding.textViewCity.setAdapter(ArrayAdapter(this@UserProfile, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,citiesList[0].data))
        })

        viewModel.errorName.observe(this, {
            binding.textViewNameError.text = it
        })

        viewModel.errorAge.observe(this, {
            binding.textViewAgeError.text = it
        })

        viewModel.errorCity.observe(this, {
            binding.textViewCityError.text = it
        })

        viewModel.buttonContinueDrawable.observe(this, {
            binding.buttonContinue.setBackgroundResource(it)
        })

        viewModel.buttonContinueEnable.observe(this, {
            binding.buttonContinue.isEnabled = it
        })

        viewModel.editTextNameDrawable.observe(this, {
            binding.editTextName.setBackgroundResource(it)
        })

        viewModel.editTextAgeDrawable.observe(this, {
            binding.editTextAge.setBackgroundResource(it)
        })

        viewModel.autocompleteCityDrawable.observe(this, {
            binding.textViewCity.setBackgroundResource(it)
        })

    }
}