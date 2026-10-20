package com.example.project_prasetio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.databinding.FragmentDonasiBinding

/**
 * DonasiFragment — Tab keempat Bottom Navigation.
 * Menampilkan halaman donasi SIAGA ID untuk mendukung program kemanusiaan.
 */
class DonasiFragment : Fragment() {

    private var _binding: FragmentDonasiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol Donasi Sekarang
        binding.btnDonasi.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Terima kasih! Fitur donasi akan segera hadir.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
