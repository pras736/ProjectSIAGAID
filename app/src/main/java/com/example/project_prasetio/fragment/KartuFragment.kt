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
import com.example.project_prasetio.databinding.FragmentKartuBinding

/**
 * KartuFragment — Screen 5 Grand Design (Kartu Saya):
 * - Header: Kartu Saya + Identitas Medis & Kontak Darurat + QR Code
 * - Digital Medical ID Card Navy: Gol O+ Rh+, Alergi Penisilin, Asma Bronkial
 * - Kontak Darurat Keluarga (Siti Rahmawati [Utama] & Dewi Lestari) dengan panggilan cepat
 * - Tombol Merah: + Buka Mode Penolong Darurat (RescuerFragment / Screen 6)
 * - Tombol Outlined: Bagikan Kartu Medis
 */
class KartuFragment : Fragment() {

    private var _binding: FragmentKartuBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKartuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = SharedPrefManager(requireContext())
        if (prefManager.username.isNotEmpty()) {
            binding.tvCardPatientName.text = prefManager.username
        }

        // QR Code button
        binding.btnQrCode.setOnClickListener {
            Toast.makeText(requireContext(), "Kode QR Medis siap dipindai oleh paramedis offline", Toast.LENGTH_SHORT).show()
        }

        // Panggilan Cepat Kontak Darurat
        binding.btnCallMother.setOnClickListener {
            callContact("0812-3456-7890", "Ibu Siti Rahmawati")
        }

        binding.btnCallWife.setOnClickListener {
            callContact("0813-9876-5432", "Dewi Lestari")
        }

        // Buka RescuerFragment (Screen 6: Mode Penolong)
        binding.btnOpenRescuerMode.setOnClickListener {
            (activity as? MainActivity)?.navigateTo(RescuerFragment.newInstance(), addToBackStack = true)
        }

        // Bagikan Kartu Medis via Intent Share
        binding.btnShareMedicalCard.setOnClickListener {
            val shareText = """
                *KARTU IDENTITAS MEDIS DARURAT (SIAGA ID)*
                Nama: ${binding.tvCardPatientName.text}
                Golongan Darah: O+ (Rh+)
                Donor Organ: Terdaftar
                Usia & JK: 28 Th • Pria
                Alergi Fatal: Penisilin, Kacang Tanah
                Kondisi Kronis: Asma Bronkial (Bawa Inhaler Ventolin)
                Kontak Darurat Utama: Siti Rahmawati (0812-3456-7890)
                
                _Disimpan lokal di SIAGA ID - Keselamatan & Tanggap Darurat_
            """.trimIndent()

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Bagikan Kartu Medis"))
        }
    }

    private fun callContact(phoneNumber: String, name: String) {
        Toast.makeText(requireContext(), "Memanggil $name ($phoneNumber)...", Toast.LENGTH_SHORT).show()
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phoneNumber.replace("-", "")}")
        }
        startActivity(dialIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
