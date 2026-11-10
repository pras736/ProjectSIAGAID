package com.example.project_prasetio.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_prasetio.R

/**
 * Tutorial3Fragment — Slide 3 Tutorial SIAGA ID
 * Tema: Kartu Medis Digital & Profil Penolong (ICE)
 */
class Tutorial3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial_3, container, false)
    }

    companion object {
        fun newInstance() = Tutorial3Fragment()
    }
}
