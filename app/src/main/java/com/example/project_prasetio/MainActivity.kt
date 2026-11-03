package com.example.project_prasetio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_prasetio.databinding.ActivityMainBinding
import com.example.project_prasetio.fragment.HomeFragment
import com.example.project_prasetio.fragment.KartuFragment
import com.example.project_prasetio.fragment.PanduanFragment

/**
 * MainActivity — Activity utama yang menjadi HOST untuk Bottom Navigation.
 *
 * Mengikuti Grand Design SIAGA ID:
 * - 3 Tab Utama:
 *   1. Beranda     → HomeFragment    (Dashboard keselamatan & ringkasan medis)
 *   2. Kartu Saya  → KartuFragment   (Kartu identitas medis digital & kontak darurat)
 *   3. Panduan     → PanduanFragment (ListView dengan Custom Adapter)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
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
                R.id.nav_kartu    -> { loadFragment(KartuFragment());   true }
                R.id.nav_panduan  -> { loadFragment(PanduanFragment()); true }
                else              -> false
            }
        }
    }

    /**
     * Membantu child fragment berpindah tab secara programatik
     */
    fun selectTab(itemId: Int) {
        binding.bottomNavigationView.selectedItemId = itemId
    }

    /**
     * Mengganti Fragment yang ditampilkan di FragmentContainerView.
     *
     * @param fragment Fragment yang akan ditampilkan
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
