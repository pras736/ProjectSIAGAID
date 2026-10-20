package com.example.project_prasetio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.databinding.FragmentBantuanBinding

/**
 * BantuanFragment — Tab ketiga Bottom Navigation.
 *
 * Menampilkan daftar kontak darurat Indonesia menggunakan:
 * - ListView dengan SIMPLE LIST ITEM (android.R.layout.simple_list_item_2)
 * - ArrayAdapter bawaan Android dengan dua baris teks (nama & nomor)
 *
 * Simple list item digunakan karena tampilan yang diperlukan cukup sederhana:
 * hanya nama layanan dan nomor telepon darurat.
 */
class BantuanFragment : Fragment() {

    private var _binding: FragmentBantuanBinding? = null
    private val binding get() = _binding!!

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

        // Data kontak darurat — format: "Nama Layanan\nNomor Telepon"
        // Menggunakan simple_list_item_2 yang secara bawaan menampilkan 2 baris teks
        val dataKontakDarurat = arrayOf(
            "🚨 Darurat Umum\n112 — Bebas Pulsa 24 Jam",
            "🚑 Ambulans\n118 / 119 — Layanan Medis Darurat",
            "🔥 Pemadam Kebakaran\n113 — Respons Darurat Kebakaran",
            "👮 Polisi\n110 — Pengaduan & Keamanan",
            "🌊 BASARNAS (SAR)\n115 — Pencarian & Penyelamatan",
            "💧 PLN (Listrik Darurat)\n123 — Gangguan Listrik",
            "☎️ Posko Bencana BNPB\n117 — Badan Nasional Penanggulangan Bencana",
            "🏥 RS Rujukan COVID / Darurat\n119 Ext 9 — Hotline Kesehatan Nasional",
            "👶 Hotline Anak (KPAI)\n119 Ext 8 — Perlindungan Anak",
            "🆘 Crisis Center Kemkes\n500-454 — Layanan Kesehatan Jiwa"
        )

        /*
         * SIMPLE LIST ITEM:
         * Menggunakan ArrayAdapter dengan layout bawaan Android
         * android.R.layout.simple_list_item_1 — satu baris teks per item
         *
         * Ini adalah cara paling sederhana untuk menampilkan ListView
         * tanpa perlu membuat layout custom atau adapter sendiri.
         */
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataKontakDarurat
        )

        // Pasang adapter ke ListView
        binding.listViewBantuan.adapter = adapter

        // Set listener klik item — tampilkan nama layanan yang diklik
        binding.listViewBantuan.setOnItemClickListener { _, _, position, _ ->
            val namaLayanan = dataKontakDarurat[position].split("\n").first()
            Toast.makeText(
                requireContext(),
                "Menghubungi: $namaLayanan",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
