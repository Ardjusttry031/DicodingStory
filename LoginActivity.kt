package com.dicoding.picodiploma.loginwithanimation.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        val title1 =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val emailtext =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val emailedit =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 0f, 1f).setDuration(100)
        val emailinput =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 0f, 1f).setDuration(100)
        val passwordtext =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 0f, 1f).setDuration(100)
        val passwordedit =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 0f, 1f)
                .setDuration(100)
        val passwordinput =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 0f, 1f).setDuration(100)
        val login =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 0f, 1f).setDuration(100)

        title1.startDelay = 100
        message.startDelay = 150
        emailtext.startDelay = 200
        emailedit.startDelay = 250
        emailinput.startDelay = 250
        passwordtext.startDelay = 300
        passwordedit.startDelay = 350
        passwordinput.startDelay = 350
        login.startDelay = 400

        AnimatorSet().apply {
            playSequentially(
                title1,
                message,
                emailtext,
                emailinput,
                emailedit,
                passwordtext,
                passwordinput,
                passwordedit,
                login
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
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString() // Ambil password dari EditText

            // Validasi email dan password
            var isValid = true

            // Validasi email
            if (!validateEmail(email)) {
                isValid = false
            }

            // Validasi password
            if (!validatePassword(password)) {
                isValid = false
            }

            // Jika semua validasi berhasil, lanjutkan dengan login
            if (isValid) {
                viewModel.saveSession(UserModel(email, "sample_token"))
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Anda berhasil login. Sudah tidak sabar untuk berbagi cerita ya?")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
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

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
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