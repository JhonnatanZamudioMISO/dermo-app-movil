package com.miso.dermoapp.ui.core.profile.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miso.dermoapp.R
import com.miso.dermoapp.databinding.ActivityUserProfileBinding
import com.miso.dermoapp.ui.core.profile.viewModels.UserProfileViewModel

class UserProfile : AppCompatActivity() {
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
    }
}