package com.example.project_prasetio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_prasetio.MainActivity
import com.example.project_prasetio.adapter.PanduanAdapter
import com.example.project_prasetio.databinding.FragmentPanduanBinding
import com.example.project_prasetio.model.PanduanItem
import com.google.android.material.tabs.TabLayout

/**
 * PanduanFragment — Screen 2 Grand Design (Panduan Tindakan):
 * - Header: "Panduan Tindakan" & "Akses luring 100% tanpa internet"
 * - Filter Kategori menggunakan TabLayout (Semua, P3K Medis, Bencana Alam, Evakuasi) (Pertemuan 10)
 * - RECYCLERVIEW DENGAN CUSTOM ADAPTER (PanduanAdapter) (Pertemuan 10)
 * - Saat item diklik, membuka DetailFragment (Screen 1)
 */
class PanduanFragment : Fragment() {

    private var _binding: FragmentPanduanBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PanduanAdapter
    private var allPanduanList: List<PanduanItem> = emptyList()
    private var activeFilter = "Semua"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPanduanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allPanduanList = getGrandDesignPanduanList()

        // 1. Inisialisasi RecyclerView & PanduanAdapter (Pertemuan 10)
        setupRecyclerView()

        // 2. Klik Search button
        binding.btnSearchPanduan.setOnClickListener {
            Toast.makeText(requireContext(), "Pencarian panduan darurat aktif offline", Toast.LENGTH_SHORT).show()
        }

        // 3. Setup TabLayout untuk Filter Kategori (Pertemuan 10)
        setupTabLayout()
    }

    /**
     * Konfigurasi RecyclerView dengan LinearLayoutManager dan Item Click Listener
     */
    private fun setupRecyclerView() {
        adapter = PanduanAdapter(allPanduanList) { item ->
            // Klik Item RecyclerView -> Buka DetailFragment (Screen 1)
            (activity as? MainActivity)?.navigateTo(
                DetailFragment.newInstance(
                    title = item.judul,
                    subtitle = item.deskripsi,
                    category = item.kategori,
                    goldenPeriod = item.goldenPeriod
                ),
                addToBackStack = true
            )
        }

        binding.recyclerViewPanduan.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@PanduanFragment.adapter
            setHasFixedSize(true)
        }
    }

    /**
     * Setup TabLayout listener untuk filter kategori (Pertemuan 10)
     */
    private fun setupTabLayout() {
        binding.tabLayoutPanduan.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val category = when (tab?.position) {
                    1 -> "P3K Medis"
                    2 -> "Bencana Alam"
                    3 -> "Evakuasi"
                    else -> "Semua"
                }
                activeFilter = category
                filterList(category)
                if (tab != null) {
                    Toast.makeText(requireContext(), "Filter: ${tab.text}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun filterList(category: String) {
        val filtered = if (category == "Semua") {
            allPanduanList
        } else {
            allPanduanList.filter { it.kategoriGroup == category }
        }
        adapter.updateData(filtered)
    }

    /**
     * Data panduan persis sesuai Screen 2 Grand Design
     */
    private fun getGrandDesignPanduanList(): List<PanduanItem> {
        return listOf(
            PanduanItem(
                judul = "RJP / CPR (Henti Jantung)",
                deskripsi = "Kompresi dada 100-120x/mnt • Golden period 4 mnt",
                kategori = "Kritis",
                kategoriGroup = "P3K Medis",
                goldenPeriod = "Golden Period: 4 Menit"
            ),
            PanduanItem(
                judul = "Manuver Heimlich (Tersedak)",
                deskripsi = "Hentakan perut melepaskan sumbatan saluran napas",
                kategori = "Kritis",
                kategoriGroup = "P3K Medis",
                goldenPeriod = "Golden Period: 3 Menit"
            ),
            PanduanItem(
                judul = "Pertolongan Luka Bakar",
                deskripsi = "Aliri air mengalir suhu ruang selama 15-20 menit",
                kategori = "P3K",
                kategoriGroup = "P3K Medis",
                goldenPeriod = "Tindakan Awal Cepat"
            ),
            PanduanItem(
                judul = "Gempa Bumi: Drop, Cover, Hold",
                deskripsi = "Perlindungan mandiri di bawah meja saat guncangan",
                kategori = "Bencana",
                kategoriGroup = "Bencana Alam",
                goldenPeriod = "Evakuasi Segera"
            ),
            PanduanItem(
                judul = "Kebakaran Gedung & Asap",
                deskripsi = "Merangkak rendah dan tutup hidung dengan kain basah",
                kategori = "Evakuasi",
                kategoriGroup = "Evakuasi",
                goldenPeriod = "Evakuasi Darurat"
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
