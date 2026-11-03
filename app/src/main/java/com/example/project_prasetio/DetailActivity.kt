package com.example.project_prasetio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivityDetailBinding

/**
 * DetailActivity — Mengimplementasikan Screen 1 Grand Design (Panduan Tindakan CPR):
 * - Header dengan tombol Back & tombol Suara Metronom (100-120 bpm)
 * - Badges: [ MENDESAK / KRITIS ] & [ Golden Period: 4 Menit ]
 * - Judul: Resusitasi Jantung Paru (CPR / RJP)
 * - Card Langkah Pertolongan Cepat (5 langkah bernomor dengan langkah ke-4 disorot oranye)
 * - Tip Irama lagu Stayin' Alive (100-120 bpm)
 * - Card Hal Yang Jangan Dilakukan (beraksen merah peringatan)
 * - Tombol Lengket Hijau di Bawah: "PANGGIL AMBULANS 119 SEKARANG"
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
        const val EXTRA_GOLDEN_PERIOD = "EXTRA_GOLDEN_PERIOD"
        const val EXTRA_GUIDE_TYPE = "EXTRA_GUIDE_TYPE"
        const val EXTRA_IMAGE_RES = "EXTRA_IMAGE_RES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Tangkap parameter Intent
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Resusitasi Jantung Paru (CPR / RJP)"
        val subtitle = intent.getStringExtra(EXTRA_DESCRIPTION) ?: "Kompresi dada darurat henti napas & henti jantung mendadak."
        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: "Kritis"
        val goldenPeriod = intent.getStringExtra(EXTRA_GOLDEN_PERIOD) ?: "Golden Period: 4 Menit"

        binding.tvDetailTitle.text = title
        binding.tvDetailSubtitle.text = subtitle
        binding.tvUrgencyBadge.text = if (category.equals("Kritis", ignoreCase = true)) "MENDESAK / KRITIS" else category.uppercase()
        binding.tvGoldenPeriodBadge.text = goldenPeriod

        // Tombol Kembali
        binding.btnBackDetail.setOnClickListener {
            finish()
        }

        // Tombol Audio Metronom (100-120 bpm)
        binding.btnSoundMetronome.setOnClickListener {
            Toast.makeText(
                this,
                "🔊 Metronom CPR aktif: 110 ketukan per menit (Stayin' Alive)",
                Toast.LENGTH_LONG
            ).show()
        }

        // Tombol Panggil Ambulans 119 Sekarang (Sticky Button)
        binding.btnCallAmbulanceNow.setOnClickListener {
            Toast.makeText(this, "Menghubungi Ambulans 119...", Toast.LENGTH_SHORT).show()
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:119")
            }
            startActivity(dialIntent)
        }
    }
}
