package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
        setupValidation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleAnimator =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val nameTextAnimator =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val nameEditLayoutAnimator =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 0f, 1f).setDuration(100)
        val nameInputAnimator =
            ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 0f, 1f).setDuration(100)
        val emailTextAnimator =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val emailEditLayoutAnimator =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 0f, 1f).setDuration(100)
        val emailInputAnimator =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 0f, 1f).setDuration(100)
        val passwordTextAnimator =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val passwordEditLayoutAnimator =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 0f, 1f).setDuration(100)
        val passwordInputAnimator =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 0f, 1f).setDuration(100)
        val signupButtonAnimator =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 0f, 1f).setDuration(100)

        titleAnimator.startDelay = 100
        nameTextAnimator.startDelay = 150
        nameEditLayoutAnimator.startDelay = 200
        nameInputAnimator.startDelay = 200
        emailTextAnimator.startDelay = 250
        emailEditLayoutAnimator.startDelay = 300
        emailInputAnimator.startDelay = 300
        passwordTextAnimator.startDelay = 350
        passwordEditLayoutAnimator.startDelay = 400
        passwordInputAnimator.startDelay = 400
        signupButtonAnimator.startDelay = 450

        AnimatorSet().apply {
            playSequentially(
                titleAnimator,
                nameTextAnimator,
                nameInputAnimator,
                nameEditLayoutAnimator,
                emailTextAnimator,
                emailInputAnimator,
                emailEditLayoutAnimator,
                passwordTextAnimator,
                passwordInputAnimator,
                passwordEditLayoutAnimator,
                signupButtonAnimator
            )
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            validateInputs()
        }
    }

    private fun setupValidation() {
        // Validasi saat kehilangan fokus
        binding.emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateEmail(binding.emailEditText.text.toString())
            }
        }

        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatePassword(binding.passwordEditText.text.toString())
            }
        }
    }

    private fun validateInputs() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        var isValid = true

        // Validasi email
        if (!validateEmail(email)) {
            isValid = false
        }

        // Validasi password
        if (!validatePassword(password)) {
            isValid = false
        }

        // Jika valid, tampilkan dialog
        if (isValid) {
            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.emailEditText.error = "Email is required"
            false
        } else {
            binding.emailEditText.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.passwordEditText.error = "Password must be at least 8 characters"
            false
        } else if (password.length < 8) {
            binding.passwordEditText.error = "Password must be at least 8 characters"
            false
        } else {
            binding.passwordEditText.error = null
            true
        }
    }
}