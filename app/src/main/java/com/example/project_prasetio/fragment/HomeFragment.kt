package com.example.project_prasetio.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.project_prasetio.DetailActivity
import com.example.project_prasetio.LoginActivity
import com.example.project_prasetio.R
import com.example.project_prasetio.SharedPrefManager
import com.example.project_prasetio.databinding.FragmentHomeBinding

/**
 * HomeFragment — Tab pertama Bottom Navigation.
 * Menampilkan dashboard utama SIAGA ID:
 * - Toolbar dengan greeting user dari SharedPreferences
 * - Kartu cepat akses (Kartu Medis, Progress Profil, Panduan CPR)
 * - Nomor penting darurat (grid 2 kolom)
 * - Tombol detail & logout
 */
class HomeFragment : Fragment() {

    // ViewBinding untuk HomeFragment (menggunakan FragmentHomeBinding)
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout fragment_home.xml menggunakan ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi SharedPreferences & tampilkan greeting username
        prefManager = SharedPrefManager(requireContext())
        binding.tvUserGreeting.text = "Halo, ${prefManager.username}"

        // Klik Card CPR / Tombol Detail -> Pindah ke DetailActivity
        val openDetailListener = {
            val title = binding.tvCardTitle.text.toString()
            val description = """
                PANDUAN DARURAT: CPR & RESUSITASI JANTUNG PARU
                
                Langkah-Langkah Pertolongan Pertama Henti Napas / Henti Jantung:
                
                1. AMANKAN LOKASI & CEK RESPON
                - Pastikan lokasi aman untuk penolong dan korban.
                - Tepuk bahu korban dan panggil dengan suara lantang: "Pak/Bu, apakah Anda sadar?"
                
                2. MINTA BANTUAN DARURAT (118 / 119)
                - Jika korban tidak merespon, segera telepon layanan medis darurat 118 / 119.
                - Minta bantuan orang sekitar untuk mengambil AED jika ada.
                
                3. CEK NAPAS & DENYUT NADI (Maksimal 10 Detik)
                - Amati dada korban apakah mengembang/mengempis secara normal.
                
                4. KOMPRESI DADA DARURAT (100-120x / Menit)
                - Posisikan tumit telapak tangan di tengah dada korban.
                - Tekan dada sedalam 5-6 cm secara cepat dan kuat.
                - Lakukan terus hingga tim medis darurat tiba.
            """.trimIndent()

            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TITLE, title)
                putExtra(DetailActivity.EXTRA_DESCRIPTION, description)
                putExtra(DetailActivity.EXTRA_IMAGE_RES, R.drawable.img_darurat)
            }
            startActivity(intent)
        }

        binding.btnDetail.setOnClickListener { openDetailListener() }
        binding.cardGuideCpr.setOnClickListener { openDetailListener() }

        // Tombol Logout di toolbar -> tampilkan AlertDialog konfirmasi
        binding.btnLogoutToolbar.setOnClickListener {
            showLogoutDialog()
        }
    }

    /**
     * Menampilkan AlertDialog konfirmasi logout.
     * Jika Ya: clear SharedPreferences & kembali ke LoginActivity.
     */
    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_logout_title))
            .setMessage(getString(R.string.dialog_logout_message))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                // Hapus sesi login
                prefManager.clearSession()
                // Pindah ke LoginActivity & bersihkan backstack
                val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    // Wajib: set _binding = null saat view dihancurkan untuk menghindari memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
