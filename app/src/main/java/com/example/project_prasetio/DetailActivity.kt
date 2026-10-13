package com.example.project_prasetio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    // Inisialisasi ViewBinding
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_IMAGE_RES = "EXTRA_IMAGE_RES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate layout dengan ViewBinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tangkap data Intent dari MainActivity
        val title = intent.getStringExtra(EXTRA_TITLE) ?: getString(R.string.guide_cpr_title)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION) ?: getString(R.string.guide_cpr_desc)
        val imageRes = intent.getIntExtra(EXTRA_IMAGE_RES, R.drawable.siaga_id)

        // Tampilkan data ke komponen UI
        binding.tvDetailTitle.text = title
        binding.tvDetailDescription.text = description
        binding.imgDetail.setImageResource(imageRes)

        // Listener Tombol Back pada Toolbar
        binding.toolbarDetail.setNavigationOnClickListener {
            finish() // Kembali ke MainActivity
        }
    }
}