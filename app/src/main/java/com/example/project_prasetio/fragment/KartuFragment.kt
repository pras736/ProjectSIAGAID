package com.example.project_prasetio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.SharedPrefManager
import com.example.project_prasetio.databinding.FragmentKartuBinding

/**
 * KartuFragment — Tab kelima Bottom Navigation.
 * Menampilkan Kartu Medis Digital pengguna SIAGA ID berdasarkan data
 * yang tersimpan di SharedPreferences.
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

        // Ambil username dari SharedPreferences untuk ditampilkan di kartu
        prefManager = SharedPrefManager(requireContext())
        val namaUser = prefManager.username

        // Tampilkan nama pengguna yang login di kartu medis
        binding.tvKartuNama.text = namaUser

        // Tombol Bagikan Kartu
        binding.btnBagikanKartu.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Fitur berbagi kartu medis akan segera hadir.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
