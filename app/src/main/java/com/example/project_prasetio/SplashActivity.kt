package com.example.project_prasetio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefManager = SharedPrefManager(this)

        // Delay 2 detik lalu cek status SharedPreferences 'isLogin'
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
 git        }, 2000)
    }

    /**
     * Pengecekan Sesi Login:
     * - Jika isLogin == true  -> Langsung masuk ke MainActivity
     * - Jika isLogin == false -> Pindah ke LoginActivity
     */
    private fun checkLoginStatus() {
        if (prefManager.isLogin) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}