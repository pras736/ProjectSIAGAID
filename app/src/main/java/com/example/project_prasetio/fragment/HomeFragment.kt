package com.example.project_prasetio.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.project_prasetio.BantuanActivity
import com.example.project_prasetio.DetailActivity
import com.example.project_prasetio.LoginActivity
import com.example.project_prasetio.MainActivity
import com.example.project_prasetio.R
import com.example.project_prasetio.RescuerActivity
import com.example.project_prasetio.SharedPrefManager
import com.example.project_prasetio.databinding.FragmentHomeBinding

/**
 * HomeFragment — Screen 3 Grand Design (Beranda SIAGA ID):
 * - Header branding SIAGA ID + Greeting user dari SharedPreferences
 * - Tombol pengaturan (membuka Bottom Sheet Screen 4)
 * - Kartu Darurat Merah "Tampilkan Kartu Medis" (membuka Mode Penolong / Screen 6)
 * - Kartu Progres "Profil Medis 70% lengkap" (membuka Kartu Saya / Screen 5)
 * - Quick Card CPR (membuka Detail Panduan CPR / Screen 1)
 * - Grid 6 Nomor Penting Darurat dengan tombol dial cepat
 * - Akses ke ListView Simple List Item via badge "Bebas Pulsa"
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = SharedPrefManager(requireContext())
        val username = if (prefManager.username.isNotEmpty()) prefManager.username else "Ahmad"
        binding.tvUserGreeting.text = "Halo, $username"

        // 1. Settings Button -> Tampilkan Pengaturan Bottom Sheet (Screen 4)
        binding.btnOpenSettings.setOnClickListener {
            val sheet = SettingsBottomSheetDialogFragment.newInstance()
            sheet.show(parentFragmentManager, SettingsBottomSheetDialogFragment.TAG)
        }

        // 2. Tombol Merah Darurat: "Tampilkan Kartu Medis" -> Buka Mode Penolong (Screen 6)
        binding.btnEmergencyCard.setOnClickListener {
            val intent = Intent(requireContext(), RescuerActivity::class.java)
            startActivity(intent)
        }

        // 3. Progres Medis -> Berpindah ke Tab Kartu Saya (Screen 5)
        val openKartuTab = {
            (activity as? MainActivity)?.selectTab(R.id.nav_kartu)
        }
        binding.cardMedicalProgress.setOnClickListener { openKartuTab() }
        binding.btnCompleteProfile.setOnClickListener { openKartuTab() }

        // 4. Panduan Darurat CPR Cepat -> Buka DetailActivity (Screen 1)
        binding.cardGuideCpr.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_GUIDE_TYPE, "CPR")
                putExtra(DetailActivity.EXTRA_TITLE, "Resusitasi Jantung Paru (CPR / RJP)")
            }
            startActivity(intent)
        }

        // 5. Section Header "Bebas Pulsa" -> Buka BantuanActivity (ListView Simple List Item)
        binding.btnOpenSimpleList.setOnClickListener {
            val intent = Intent(requireContext(), BantuanActivity::class.java)
            startActivity(intent)
        }

        // 6. Action Panggilan Cepat (Grid 2 Kolom x 3 Baris)
        binding.btnCall112.setOnClickListener { makePhoneCall("112") }
        binding.cardCall112.setOnClickListener { makePhoneCall("112") }

        binding.btnCallAmbulance.setOnClickListener { makePhoneCall("119") }
        binding.cardCallAmbulance.setOnClickListener { makePhoneCall("119") }

        binding.btnCallFire.setOnClickListener { makePhoneCall("113") }
        binding.cardCallFire.setOnClickListener { makePhoneCall("113") }

        binding.btnCallPolice.setOnClickListener { makePhoneCall("110") }
        binding.cardCallPolice.setOnClickListener { makePhoneCall("110") }

        binding.btnCallBpbd.setOnClickListener { makePhoneCall("117") }
        binding.cardCallBpbd.setOnClickListener { makePhoneCall("117") }

        binding.btnCallBasarnas.setOnClickListener { makePhoneCall("115") }
        binding.cardCallBasarnas.setOnClickListener { makePhoneCall("115") }
    }

    private fun makePhoneCall(phoneNumber: String) {
        Toast.makeText(requireContext(), "Memanggil layanan darurat: $phoneNumber", Toast.LENGTH_SHORT).show()
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(dialIntent)
    }

    /**
     * Menampilkan AlertDialog konfirmasi logout (juga dapat dipanggil langsung)
     */
    fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_logout_title))
            .setMessage(getString(R.string.dialog_logout_message))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                prefManager.clearSession()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
