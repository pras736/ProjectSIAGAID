package com.example.project_prasetio.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.project_prasetio.LoginActivity
import com.example.project_prasetio.MainActivity
import com.example.project_prasetio.SharedPrefManager
import com.example.project_prasetio.databinding.ActivityTutorialBinding

/**
 * TutorialActivity — Tutorial / Onboard Screen berbasis ViewPager2 & DotsIndicator (Pertemuan 9):
 * - Memperkenalkan fitur utama SIAGA ID (Tanggap Darurat, Panduan Offline, Kartu Medis)
 * - Tampil setelah SplashScreen pada instalasi pertama / akses dari pengaturan
 * - Dilengkapi penanda titik halaman (DotsIndicator) & tombol navigasi (Lewati, Lanjut, Mulai Sekarang)
 */
class TutorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding
    private lateinit var prefManager: SharedPrefManager
    private var isFromSettings: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefManager = SharedPrefManager(this)
        isFromSettings = intent.getBooleanExtra(EXTRA_FROM_SETTINGS, false)

        // 1. Siapkan daftar 3 Fragment Tutorial bertema SIAGA ID (Pertemuan 9 Tahap 5.1 & 7.3)
        val fragmentsList = listOf(
            Tutorial1Fragment.newInstance(),
            Tutorial2Fragment.newInstance(),
            Tutorial3Fragment.newInstance()
        )

        // 2. Pasang Adapter ke ViewPager2 (Pertemuan 9 Tahap 5.3)
        val adapter = TutorialPagerAdapter(this, fragmentsList)
        binding.viewPagerTutorial.adapter = adapter

        // 3. Hubungkan DotsIndicator dengan ViewPager2 (Pertemuan 9 Tahap 6.3)
        binding.dotIndicator.attachTo(binding.viewPagerTutorial)

        // 4. Deteksi perpindahan slide untuk memperbarui tombol Lewati & Lanjut / Mulai
        binding.viewPagerTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val isLastPage = (position == fragmentsList.size - 1)
                binding.btnNext.text = if (isLastPage) "Mulai Sekarang" else "Lanjut"
                binding.btnSkip.visibility = if (isLastPage) View.INVISIBLE else View.VISIBLE
            }
        })

        // 5. Tombol Lewati (Pertemuan 9 Tantangan 7.4)
        binding.btnSkip.setOnClickListener {
            finishTutorial()
        }

        // 6. Tombol Lanjut / Mulai Sekarang
        binding.btnNext.setOnClickListener {
            val currentPos = binding.viewPagerTutorial.currentItem
            if (currentPos < fragmentsList.size - 1) {
                binding.viewPagerTutorial.currentItem = currentPos + 1
            } else {
                finishTutorial()
            }
        }
    }

    /**
     * Menyimpan penanda tutorial sudah dilihat dan berpindah ke alur aplikasi berikutnya
     */
    private fun finishTutorial() {
        prefManager.isTutorialSeen = true

        if (isFromSettings) {
            finish()
        } else {
            if (prefManager.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_FROM_SETTINGS = "extra_from_settings"
    }
}
