package com.example.project_prasetio

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_prasetio.databinding.ActivityMainBinding
import com.example.project_prasetio.fragment.BantuanFragment
import com.example.project_prasetio.fragment.DetailFragment
import com.example.project_prasetio.fragment.HomeFragment
import com.example.project_prasetio.fragment.KartuFragment
import com.example.project_prasetio.fragment.PanduanFragment
import com.example.project_prasetio.fragment.RescuerFragment

/**
 * MainActivity — Host Activity utama berbasis Fragment (Single Activity Architecture):
 *
 * Menerapkan Grand Design SIAGA ID:
 * - 3 Tab Utama:
 *   1. Beranda     → HomeFragment
 *   2. Kartu Saya  → KartuFragment
 *   3. Panduan     → PanduanFragment (Custom Adapter ListView)
 * - Sub-screen Fragment:
 *   - DetailFragment   (Screen 1: Detail Panduan CPR)
 *   - RescuerFragment  (Screen 6: Mode Penolong)
 *   - BantuanFragment  (ListView Simple List Item)
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
            loadFragment(HomeFragment(), addToBackStack = false)
            binding.bottomNavigationView.selectedItemId = R.id.nav_beranda
        }

        // Set listener perubahan tab Bottom Navigation
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_beranda  -> { loadFragment(HomeFragment(), addToBackStack = false);    true }
                R.id.nav_kartu    -> { loadFragment(KartuFragment(), addToBackStack = false);   true }
                R.id.nav_panduan  -> { loadFragment(PanduanFragment(), addToBackStack = false); true }
                else              -> false
            }
        }

        // Listener perubahan BackStack untuk mengatur visibilitas Bottom Navigation
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            when (currentFragment) {
                is DetailFragment, is RescuerFragment, is BantuanFragment -> {
                    setBottomNavVisibility(false)
                }
                is HomeFragment -> {
                    setBottomNavVisibility(true)
                    binding.bottomNavigationView.menu.findItem(R.id.nav_beranda)?.isChecked = true
                }
                is KartuFragment -> {
                    setBottomNavVisibility(true)
                    binding.bottomNavigationView.menu.findItem(R.id.nav_kartu)?.isChecked = true
                }
                is PanduanFragment -> {
                    setBottomNavVisibility(true)
                    binding.bottomNavigationView.menu.findItem(R.id.nav_panduan)?.isChecked = true
                }
                else -> {
                    setBottomNavVisibility(true)
                }
            }
        }

        // Back Press Handler
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    /**
     * Berpindah ke tab Bottom Navigation secara programatik
     */
    fun selectTab(itemId: Int) {
        binding.bottomNavigationView.selectedItemId = itemId
    }

    /**
     * Navigasi ke Fragment lain (misal: DetailFragment, RescuerFragment, BantuanFragment)
     */
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        loadFragment(fragment, addToBackStack)
    }

    /**
     * Mengatur visibilitas Bottom Navigation Bar
     */
    fun setBottomNavVisibility(visible: Boolean) {
        binding.bottomNavigationView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * Mengganti Fragment yang ditampilkan di FragmentContainerView
     */
    private fun loadFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}
