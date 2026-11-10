package com.example.project_prasetio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.MainActivity
import com.example.project_prasetio.R
import com.example.project_prasetio.adapter.PanduanAdapter
import com.example.project_prasetio.databinding.FragmentPanduanBinding
import com.example.project_prasetio.model.PanduanItem
import com.google.android.material.chip.Chip

/**
 * PanduanFragment — Screen 2 Grand Design (Panduan Tindakan):
 * - Header: "Panduan Tindakan" & "Akses luring 100% tanpa internet"
 * - Filter Chips: Semua, P3K Medis, Bencana Alam, Evakuasi
 * - LISTVIEW DENGAN CUSTOM ADAPTER (PanduanAdapter)
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
        adapter = PanduanAdapter(requireContext(), allPanduanList)
        binding.listViewPanduan.adapter = adapter

        // Klik Search button
        binding.btnSearchPanduan.setOnClickListener {
            Toast.makeText(requireContext(), "Pencarian panduan darurat aktif offline", Toast.LENGTH_SHORT).show()
        }

        // Setup filter chips
        setupFilterChips()

        // Klik Item ListView -> Buka DetailFragment (Screen 1)
        binding.listViewPanduan.setOnItemClickListener { _, _, position, _ ->
            val item = (adapter.getItem(position) as PanduanItem)
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
    }
    // Deteksi chip yang dipilih menggunakan ChipGroup listener (Pertemuan 9 Tahap 2.2)

    private fun setupFilterChips() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull() ?: R.id.chipSemua
            val chip = group.findViewById<Chip>(selectedChipId)
            val category = when (selectedChipId) {
                R.id.chipP3K -> "P3K Medis"
                R.id.chipBencana -> "Bencana Alam"
                R.id.chipEvakuasi -> "Evakuasi"
                else -> "Semua"
            }
            activeFilter = category
            filterList(category)
            if (chip != null) {
                Toast.makeText(requireContext(), "Filter: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }
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
