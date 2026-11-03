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
import com.example.project_prasetio.databinding.FragmentDetailBinding

/**
 * DetailFragment — Screen 1 Grand Design (Detail Panduan CPR) berbasis Fragment:
 * - Header dengan tombol Back & tombol Suara Metronom (100-120 bpm)
 * - Badges: [ MENDESAK / KRITIS ] & [ Golden Period: 4 Menit ]
 * - Judul: Resusitasi Jantung Paru (CPR / RJP)
 * - Card Langkah Pertolongan Cepat (5 langkah bernomor)
 * - Tip Irama lagu Stayin' Alive (100-120 bpm)
 * - Card Hal Yang Jangan Dilakukan
 * - Tombol Lengket Hijau di Bawah: "PANGGIL AMBULANS 119 SEKARANG"
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_SUBTITLE = "arg_subtitle"
        private const val ARG_CATEGORY = "arg_category"
        private const val ARG_GOLDEN_PERIOD = "arg_golden_period"

        fun newInstance(
            title: String = "Resusitasi Jantung Paru (CPR / RJP)",
            subtitle: String = "Kompresi dada darurat henti napas & henti jantung mendadak.",
            category: String = "Kritis",
            goldenPeriod: String = "Golden Period: 4 Menit"
        ): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_SUBTITLE, subtitle)
                    putString(ARG_CATEGORY, category)
                    putString(ARG_GOLDEN_PERIOD, goldenPeriod)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sembunyikan bottom navigation saat di halaman detail
        (activity as? MainActivity)?.setBottomNavVisibility(false)

        val title = arguments?.getString(ARG_TITLE) ?: "Resusitasi Jantung Paru (CPR / RJP)"
        val subtitle = arguments?.getString(ARG_SUBTITLE) ?: "Kompresi dada darurat henti napas & henti jantung mendadak."
        val category = arguments?.getString(ARG_CATEGORY) ?: "Kritis"
        val goldenPeriod = arguments?.getString(ARG_GOLDEN_PERIOD) ?: "Golden Period: 4 Menit"

        binding.tvDetailTitle.text = title
        binding.tvDetailSubtitle.text = subtitle
        binding.tvUrgencyBadge.text = if (category.equals("Kritis", ignoreCase = true)) "MENDESAK / KRITIS" else category.uppercase()
        binding.tvGoldenPeriodBadge.text = goldenPeriod

        // Tombol Kembali
        binding.btnBackDetail.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Tombol Audio Metronom (100-120 bpm)
        binding.btnSoundMetronome.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Metronom CPR aktif: 110 ketukan per menit (Stayin' Alive)",
                Toast.LENGTH_LONG
            ).show()
        }

        // Tombol Panggil Ambulans 119 Sekarang (Sticky Button)
        binding.btnCallAmbulanceNow.setOnClickListener {
            Toast.makeText(requireContext(), "Menghubungi Ambulans 119...", Toast.LENGTH_SHORT).show()
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:119")
            }
            startActivity(dialIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Tampilkan kembali bottom navigation saat keluar dari detail
        (activity as? MainActivity)?.setBottomNavVisibility(true)
        _binding = null
    }
}
