package com.example.project_prasetio.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_prasetio.R

/**
 * Tutorial2Fragment — Slide 2 Tutorial SIAGA ID
 * Tema: Panduan Tindakan Medis & P3K 100% Offline (CPR, Heimlich, Bencana)
 */
class Tutorial2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial_2, container, false)
    }

    companion object {
        fun newInstance() = Tutorial2Fragment()
    }
}
