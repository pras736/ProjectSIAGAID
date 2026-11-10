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
import com.example.project_prasetio.tutorial.TutorialActivity

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

        // Delay 2 detik lalu navigasi ke Tutorial / Login / Main
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, 2000)
    }

    /**
     * Alur Navigasi Setelah Splash Screen:
     * 1. Jika tutorial belum pernah dilihat -> Masuk ke TutorialActivity (3 Slide ViewPager2 + DotsIndicator)
     * 2. Jika tutorial sudah dilihat & isLogin == true -> Langsung masuk ke MainActivity
     * 3. Jika tutorial sudah dilihat & isLogin == false -> Pindah ke LoginActivity
     */
    private fun checkLoginStatus() {
        if (!prefManager.isTutorialSeen) {
            val intent = Intent(this@SplashActivity, TutorialActivity::class.java)
            startActivity(intent)
        } else if (prefManager.isLogin) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
