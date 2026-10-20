package com.example.project_prasetio.model

/**
 * Data class untuk merepresentasikan satu item Panduan Darurat.
 *
 * @param emoji       Ikon emoji sebagai representasi visual kategori
 * @param judul       Judul panduan darurat
 * @param deskripsi   Deskripsi singkat panduan
 * @param kategori    Badge kategori (mis: "Jantung", "Luka", "Bencana")
 * @param isiLengkap  Konten detail panduan yang akan ditampilkan di DetailActivity
 */
data class PanduanItem(
    val emoji: String,
    val judul: String,
    val deskripsi: String,
    val kategori: String,
    val isiLengkap: String
)
