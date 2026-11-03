package com.example.project_prasetio.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.MainActivity
import com.example.project_prasetio.SharedPrefManager
import com.example.project_prasetio.databinding.FragmentRescuerBinding

/**
 * RescuerFragment — Screen 6 Grand Design (Mode Penolong) berbasis Fragment:
 * - Header Merah Gawat Darurat dengan tombol keluar (X)
 * - Banner Peringatan untuk Penolong / Paramedis Medis
 * - Identitas Korban
 * - Golongan Darah Besar: O+ (Rhesus Positif)
 * - Alergi Fatal (Penisilin) & Kondisi Kronis (Asma Bronkial)
 * - Tombol Panggilan Cepat Hijau: HUBUNGI IBU (0812-3456-7890)
 * - Instruksi Pertolongan Pertama Medis
 * - Tombol: "Tutup & Kembali ke Beranda"
 */
class RescuerFragment : Fragment() {

    private var _binding: FragmentRescuerBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: SharedPrefManager

    companion object {
        fun newInstance(): RescuerFragment = RescuerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRescuerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sembunyikan bottom navigation saat di Mode Penolong
        (activity as? MainActivity)?.setBottomNavVisibility(false)

        prefManager = SharedPrefManager(requireContext())
        if (prefManager.username.isNotEmpty()) {
            binding.tvRescuerPatientName.text = prefManager.username
        }

        // Tombol Tutup (X) di header merah
        binding.btnCloseRescuer.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Tombol Tutup & Kembali ke Beranda di bagian bawah
        binding.btnBackToHome.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Tombol Hijau Besar: Hubungi Ibu
        binding.btnCallMotherRescuer.setOnClickListener {
            Toast.makeText(requireContext(), "Menghubungi Kontak Utama Ibu (0812-3456-7890)...", Toast.LENGTH_SHORT).show()
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:081234567890")
            }
            startActivity(dialIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Tampilkan kembali bottom navigation saat keluar dari Mode Penolong
        (activity as? MainActivity)?.setBottomNavVisibility(true)
        _binding = null
    }
}
