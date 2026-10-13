package com.example.project_prasetio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ViewBinding & SharedPreferences Manager
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate layout dengan ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi SharedPreferences Manager & Tampilkan Greeting User
        prefManager = SharedPrefManager(this)
        val savedUsername = prefManager.username
        binding.tvUserGreeting.text = "${getString(R.string.main_greeting)}$savedUsername"

        // Klik Card Panduan / Tombol Detail -> Pindah ke DetailActivity
        val openDetailListener = {
            val title = binding.tvCardTitle.text.toString()
            val description = """
                PANDUAN DARURAT: CPR & RESUSITASI JANTUNG PARU
                
                Langkah-Langkah Pertolongan Pertama Henti Napas / Henti Jantung:
                
                1. AMANKAN LOKASI & CEK RESPON
                - Pastikan lokasi aman untuk penolong dan korban.
                - Tepuk bahu korban dan panggil dengan suara lantang: "Pak/Bu, apakah Anda sadar?"
                
                2. MINTA BANTUAN DARURAT (118 / 119)
                - Jika korban tidak merespon, segera telepon layanan medis darurat 118 / 119.
                - Minta bantuan orang sekitar untuk mengambil AED (Defibrilator Otomatis) jika ada.
                
                3. CEK NAPAS & DENYUT NADI (Maksimal 10 Detik)
                - Amati dada korban apakah mengembang/mengempis secara normal.
                
                4. KOMPRESI DADA DARURAT (100-120x / Menit)
                - Posisikan tumit telapak tangan di tengah dada korban (seperdua bawah tulang dada).
                - Kaitkan jari-jari tangan kedua di atas tangan pertama.
                - Tekan dada sedalam 5-6 cm secara cepat dan kuat ritme 100-120 kali per menit.
                - Lakukan terus hingga tim medis darurat tiba di lokasi.
            """.trimIndent()

            val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TITLE, title)
                putExtra(DetailActivity.EXTRA_DESCRIPTION, description)
                putExtra(DetailActivity.EXTRA_IMAGE_RES, R.drawable.img_darurat)
            }
            startActivity(intent)
        }

        binding.btnDetail.setOnClickListener { openDetailListener() }
        binding.cardGuideCpr.setOnClickListener { openDetailListener() }

        // Klik Tombol LogOut -> Tampilkan AlertDialog Konfirmasi
        binding.btnLogout.setOnClickListener {
            showLogoutAlertDialog()
        }
    }

    /**
     * Menampilkan AlertDialog Konfirmasi Keluar/Logout
     */
    private fun showLogoutAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_logout_title))
            .setMessage(getString(R.string.dialog_logout_message))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                // 1. Hapus/clear sesi login di SharedPreferences (isLogin = false)
                prefManager.clearSession()

                // 2. Pindah ke LoginActivity & bersihkan backstack
                val intent = Intent(this@MainActivity, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
                // Batal keluar, tutup dialog
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}