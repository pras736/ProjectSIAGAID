package com.example.project_prasetio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivityBantuanBinding

/**
 * BantuanActivity — Mengimplementasikan persyaratan tugas:
 * LISTVIEW DENGAN SIMPLE LIST ITEM (android.R.layout.simple_list_item_2)
 *
 * Menampilkan daftar kontak darurat resmi Indonesia menggunakan 2 baris teks:
 * - text1: Nama Layanan & Nomor
 * - text2: Deskripsi & Status Bebas Pulsa
 */
class BantuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBantuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBantuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbarBantuan.setNavigationOnClickListener {
            finish()
        }

        // Data kontak darurat Indonesia
        val kontakList = listOf(
            mapOf("name" to "🚨 Darurat Umum (112)", "desc" to "Layanan Darurat Terpadu Nasional • Bebas Pulsa 24 Jam", "number" to "112"),
            mapOf("name" to "🚑 Ambulans Gawat Darurat (118 / 119)", "desc" to "Kemenkes RI & PMI • Pertolongan Medis Cepat", "number" to "119"),
            mapOf("name" to "🔥 Pemadam Kebakaran (113)", "desc" to "Penanganan Kebakaran & Evakuasi Bahaya", "number" to "113"),
            mapOf("name" to "👮 Kepolisian Republik Indonesia (110)", "desc" to "Keamanan, Ketertiban & Laporan Darurat", "number" to "110"),
            mapOf("name" to "🌊 BASARNAS SAR (115)", "desc" to "Pencarian & Pertolongan Bencana Alam / Air", "number" to "115"),
            mapOf("name" to "🏛️ Posko Siaga Bencana BNPB (117)", "desc" to "Badan Nasional Penanggulangan Bencana", "number" to "117"),
            mapOf("name" to "⚡ PLN Gangguan Listrik (123)", "desc" to "Bahaya Konsleting & Kabel Putus Darurat", "number" to "123"),
            mapOf("name" to "🏥 Hotline Kesehatan Jiwa (119 Ext 8)", "desc" to "Konseling & Dukungan Psikologis Darurat", "number" to "119")
        )

        // Menggunakan SimpleAdapter dengan android.R.layout.simple_list_item_2 (Simple List Item)
        val adapter = SimpleAdapter(
            this,
            kontakList,
            android.R.layout.simple_list_item_2,
            arrayOf("name", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listViewBantuan.adapter = adapter

        // Klik item -> panggil nomor darurat
        binding.listViewBantuan.setOnItemClickListener { _, _, position, _ ->
            val kontak = kontakList[position]
            val nomor = kontak["number"] ?: "112"
            Toast.makeText(this, "Menghubungi: ${kontak["name"]}", Toast.LENGTH_SHORT).show()

            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$nomor")
            }
            startActivity(dialIntent)
        }
    }
}
