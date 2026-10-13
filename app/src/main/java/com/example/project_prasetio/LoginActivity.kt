package com.example.project_prasetio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_prasetio.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // 1. Inisialisasi ViewBinding untuk mengakses elemen UI secara type-safe (tanpa findViewById)
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 2. Menginflasi layout menggunakan ActivityLoginBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Inisialisasi pengelola SharedPreferences
        prefManager = SharedPrefManager(this)

        // 4. Set listener pada tombol Login
        binding.btnlogin.setOnClickListener {
            performLoginValidation()
        }
    }

    /**
     * PENJELASAN AUTENTIKASI KONDISI LOGIN:
     *
     * Fungsi ini mengambil input dari EditText username dan password,
     * lalu memeriksa apakah username dan password yang diketikkan SAMA.
     *
     * Syarat Login Berhasil:
     * - Field username dan password tidak boleh kosong.
     * - Username yang diketik HARUS SAMA PERSIS dengan Password yang diketik (misal: "tio" & "tio").
     */
    private fun performLoginValidation() {
        // Ambil teks dari input field
        val usernameInput = binding.edtusername.text.toString().trim()
        val passwordInput = binding.edtpassword.text.toString().trim()

        Log.d("LoginActivity", "Proses Autentikasi -> Username: '$usernameInput' | Password: '$passwordInput'")

        // LANGKAH 1: Cek apakah input field ada yang kosong
        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_field), Toast.LENGTH_SHORT).show()
            return
        }

        // LANGKAH 2: Cek kondisi apakah Username SAMA DENGAN Password yang diketikkan
        val isValidUser = (usernameInput == passwordInput)

        if (isValidUser) {
            // LANGKAH 3: Autentikasi BERHASIL (Username & Password SAMA)
            Toast.makeText(this, "Login Berhasil! Selamat datang, $usernameInput", Toast.LENGTH_SHORT).show()

            // LANGKAH 4: Simpan status SharedPreferences isLogin = true dan username pengguna
            prefManager.saveLoginSession(usernameInput)

            // LANGKAH 5: Pindah ke MainActivity menggunakan Intent
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)

            // LANGKAH 6: Panggil finish() agar LoginActivity ditutup dari backstack
            finish()
        } else {
            // LANGKAH 7: Autentikasi GAGAL (Username & Password TIDAK SAMA)
            Toast.makeText(this, getString(R.string.error_invalid_login), Toast.LENGTH_LONG).show()
        }
    }
}