package com.example.project_prasetio.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.project_prasetio.DetailActivity
import com.example.project_prasetio.R
import com.example.project_prasetio.adapter.PanduanAdapter
import com.example.project_prasetio.databinding.FragmentPanduanBinding
import com.example.project_prasetio.model.PanduanItem

/**
 * PanduanFragment — Screen 2 Grand Design (Panduan Tindakan):
 * - Header: "Panduan Tindakan" & "Akses luring 100% tanpa internet"
 * - Filter Chips: Semua, P3K Medis, Bencana Alam, Evakuasi
 * - LISTVIEW DENGAN CUSTOM ADAPTER (PanduanAdapter)
 * - Saat item diklik, membuka DetailActivity (Screen 1)
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

        // Klik Item ListView -> Buka DetailActivity (Screen 1)
        binding.listViewPanduan.setOnItemClickListener { _, _, position, _ ->
            val item = (adapter.getItem(position) as PanduanItem)
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TITLE, item.judul)
                putExtra(DetailActivity.EXTRA_DESCRIPTION, item.deskripsi)
                putExtra(DetailActivity.EXTRA_CATEGORY, item.kategori)
                putExtra(DetailActivity.EXTRA_GOLDEN_PERIOD, item.goldenPeriod)
            }
            startActivity(intent)
        }
    }

    private fun setupFilterChips() {
        val chips = listOf(
            binding.chipSemua to "Semua",
            binding.chipP3K to "P3K Medis",
            binding.chipBencana to "Bencana Alam",
            binding.chipEvakuasi to "Evakuasi"
        )

        chips.forEach { (view, filterName) ->
            view.setOnClickListener {
                activeFilter = filterName
                updateChipStyles(chips, view)
                filterList(filterName)
            }
        }
    }

    private fun updateChipStyles(chips: List<Pair<TextView, String>>, selectedView: TextView) {
        chips.forEach { (view, _) ->
            if (view == selectedView) {
                view.setBackgroundResource(R.drawable.bg_chip_selected)
                view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                view.setBackgroundResource(R.drawable.bg_chip_unselected)
                view.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
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
