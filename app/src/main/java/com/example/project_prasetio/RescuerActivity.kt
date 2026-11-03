package com.example.project_prasetio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivityRescuerBinding

/**
 * RescuerActivity — Mengimplementasikan Screen 6 Grand Design (Mode Penolong):
 * - Header Merah Gawat Darurat dengan tombol keluar (X)
 * - Banner Peringatan untuk Penolong / Paramedis Medis
 * - Identitas Korban
 * - Golongan Darah Besar: O+ (Rhesus Positif)
 * - Alergi Fatal (Penisilin) & Kondisi Kronis (Asma Bronkial)
 * - Tombol Panggilan Cepat Hijau: HUBUNGI IBU (0812-3456-7890)
 * - Instruksi Pertolongan Pertama Medis
 * - Tombol: "Tutup & Kembali ke Beranda"
 */
class RescuerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRescuerBinding
    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRescuerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        prefManager = SharedPrefManager(this)
        if (prefManager.username.isNotEmpty()) {
            binding.tvRescuerPatientName.text = prefManager.username
        }

        // Tombol Tutup (X) di header merah
        binding.btnCloseRescuer.setOnClickListener {
            finish()
        }

        // Tombol Tutup & Kembali ke Beranda di bagian bawah
        binding.btnBackToHome.setOnClickListener {
            finish()
        }

        // Tombol Hijau Besar: Hubungi Ibu
        binding.btnCallMotherRescuer.setOnClickListener {
            Toast.makeText(this, "Menghubungi Kontak Utama Ibu (0812-3456-7890)...", Toast.LENGTH_SHORT).show()
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:081234567890")
            }
            startActivity(dialIntent)
        }
    }
}
