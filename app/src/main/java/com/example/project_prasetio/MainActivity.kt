package com.example.project_prasetio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_prasetio.databinding.ActivityMainBinding
import com.example.project_prasetio.fragment.BantuanFragment
import com.example.project_prasetio.fragment.DonasiFragment
import com.example.project_prasetio.fragment.HomeFragment
import com.example.project_prasetio.fragment.KartuFragment
import com.example.project_prasetio.fragment.PanduanFragment

/**
 * MainActivity — Activity utama yang menjadi HOST untuk Bottom Navigation.
 *
 * Arsitektur: Single Activity + Multiple Fragments
 * - MainActivity hanya bertanggung jawab mengelola navigasi antar Fragment
 * - Setiap tab Bottom Navigation meload Fragment yang sesuai
 * - Konten masing-masing tab dikelola di dalam Fragment-nya sendiri
 *
 * Tab yang tersedia:
 * 1. Beranda     → HomeFragment    (dashboard utama)
 * 2. Panduan     → PanduanFragment (ListView + Custom Adapter)
 * 3. Bantuan     → BantuanFragment (ListView + Simple List Item)
 * 4. Donasi      → DonasiFragment  (halaman donasi)
 * 5. Kartu Saya  → KartuFragment   (kartu medis digital)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate layout menggunakan ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tampilkan HomeFragment sebagai tab default saat pertama kali dibuka
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            binding.bottomNavigationView.selectedItemId = R.id.nav_beranda
        }

        // Set listener perubahan tab Bottom Navigation
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_beranda  -> { loadFragment(HomeFragment());    true }
                R.id.nav_panduan  -> { loadFragment(PanduanFragment()); true }
                R.id.nav_bantuan  -> { loadFragment(BantuanFragment()); true }
                R.id.nav_donasi   -> { loadFragment(DonasiFragment());  true }
                R.id.nav_kartu    -> { loadFragment(KartuFragment());   true }
                else              -> false
            }
        }
    }

    /**
     * Mengganti Fragment yang ditampilkan di FragmentContainerView.
     * Menggunakan replace() agar hanya satu Fragment aktif dalam satu waktu.
     *
     * @param fragment Fragment yang akan ditampilkan
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
