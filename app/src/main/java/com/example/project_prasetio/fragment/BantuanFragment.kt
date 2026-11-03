package com.example.project_prasetio.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.MainActivity
import com.example.project_prasetio.databinding.FragmentBantuanBinding

/**
 * BantuanFragment — ListView dengan Simple List Item (android.R.layout.simple_list_item_2):
 * - Menampilkan daftar kontak darurat Indonesia secara sederhana (2 baris teks)
 * - Memiliki navigasi tombol Back untuk kembali ke Beranda
 */
class BantuanFragment : Fragment() {

    private var _binding: FragmentBantuanBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): BantuanFragment = BantuanFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBantuanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setBottomNavVisibility(false)

        binding.toolbarBantuan.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val kontakList = listOf(
            mapOf("name" to "Darurat Umum (112)", "desc" to "Layanan Darurat Terpadu Nasional • Bebas Pulsa 24 Jam", "number" to "112"),
            mapOf("name" to "Ambulans Gawat Darurat (118 / 119)", "desc" to "Kemenkes RI & PMI • Pertolongan Medis Cepat", "number" to "119"),
            mapOf("name" to "Pemadam Kebakaran (113)", "desc" to "Penanganan Kebakaran & Evakuasi Bahaya", "number" to "113"),
            mapOf("name" to "Kepolisian Republik Indonesia (110)", "desc" to "Keamanan, Ketertiban & Laporan Darurat", "number" to "110"),
            mapOf("name" to "BASARNAS SAR (115)", "desc" to "Pencarian & Pertolongan Bencana Alam / Air", "number" to "115"),
            mapOf("name" to "Posko Siaga Bencana BNPB (117)", "desc" to "Badan Nasional Penanggulangan Bencana", "number" to "117"),
            mapOf("name" to "PLN Gangguan Listrik (123)", "desc" to "Bahaya Konsleting & Kabel Putus Darurat", "number" to "123"),
            mapOf("name" to "Hotline Kesehatan Jiwa (119 Ext 8)", "desc" to "Konseling & Dukungan Psikologis Darurat", "number" to "119")
        )

        // Menggunakan SimpleAdapter dengan android.R.layout.simple_list_item_2 (Simple List Item)
        val adapter = SimpleAdapter(
            requireContext(),
            kontakList,
            android.R.layout.simple_list_item_2,
            arrayOf("name", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listViewBantuan.adapter = adapter

        binding.listViewBantuan.setOnItemClickListener { _, _, position, _ ->
            val kontak = kontakList[position]
            val nomor = kontak["number"] ?: "112"
            Toast.makeText(requireContext(), "Menghubungi: ${kontak["name"]}", Toast.LENGTH_SHORT).show()

            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$nomor")
            }
            startActivity(dialIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setBottomNavVisibility(true)
        _binding = null
    }
}
