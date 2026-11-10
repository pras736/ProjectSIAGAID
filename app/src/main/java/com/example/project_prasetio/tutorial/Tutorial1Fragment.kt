package com.example.project_prasetio.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_prasetio.R

/**
 * Tutorial1Fragment — Slide 1 Tutorial SIAGA ID
 * Tema: Panggilan Cepat Tanggap Darurat (112, 119, 113, 115)
 */
class Tutorial1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial_1, container, false)
    }

    companion object {
        fun newInstance() = Tutorial1Fragment()
    }
}
