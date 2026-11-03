package com.example.project_prasetio.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project_prasetio.LoginActivity
import com.example.project_prasetio.R
import com.example.project_prasetio.SharedPrefManager
import com.example.project_prasetio.databinding.FragmentSettingsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * SettingsBottomSheetDialogFragment — Mengimplementasikan Screen 4 Grand Design (Pengaturan Bottom Sheet)
 * - Tampilan preferensi (Mode gelap, Suara panduan, Ukuran teks)
 * - Zona bahaya: "Hapus semua data" yang memicu ALERT DIALOG konfirmasi logout
 * - Informasi versi, sumber referensi Kemenkes/PMI/BNPB, dan disclaimer
 */
class SettingsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingsBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = SharedPrefManager(requireContext())

        // Tombol Close
        binding.btnCloseSheet.setOnClickListener {
            dismiss()
        }

        // Switch mode gelap demo toast
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                requireContext(),
                if (isChecked) "Mode Gelap Diaktifkan" else "Mode Gelap Dinonaktifkan",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Switch suara panduan demo
        binding.switchVoiceGuidance.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                requireContext(),
                if (isChecked) "Suara panduan & metronom aktif" else "Suara panduan dinonaktifkan",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Ukuran teks selector
        binding.btnTextSmall.setOnClickListener {
            Toast.makeText(requireContext(), "Ukuran teks: Kecil", Toast.LENGTH_SHORT).show()
        }
        binding.btnTextMedium.setOnClickListener {
            Toast.makeText(requireContext(), "Ukuran teks: Sedang (Default)", Toast.LENGTH_SHORT).show()
        }
        binding.btnTextLarge.setOnClickListener {
            Toast.makeText(requireContext(), "Ukuran teks: Besar", Toast.LENGTH_SHORT).show()
        }

        // Tombol Hapus Semua Data -> Tampilkan ALERT DIALOG saat logout
        binding.btnDeleteAllData.setOnClickListener {
            showLogoutAlertDialog()
        }
    }

    /**
     * Menampilkan AlertDialog konfirmasi keluar / hapus data sesi sesuai kriteria tugas
     */
    private fun showLogoutAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_logout_title))
            .setMessage(getString(R.string.dialog_logout_message))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                // Bersihkan sesi di SharedPreferences
                prefManager.clearSession()
                Toast.makeText(requireContext(), "Sesi berhasil dihapus. Sampai jumpa!", Toast.LENGTH_SHORT).show()

                dismiss()

                // Arahkan kembali ke LoginActivity dan bersihkan backstack
                val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                activity?.finish()
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

    companion object {
        const val TAG = "SettingsBottomSheet"
        fun newInstance() = SettingsBottomSheetDialogFragment()
    }
}
