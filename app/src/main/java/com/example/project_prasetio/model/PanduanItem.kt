package com.example.project_prasetio.model

/**
 * Data class untuk merepresentasikan satu item Panduan Darurat sesuai Grand Design.
 *
 * @param judul       Judul panduan darurat (misal: "RJP / CPR (Henti Jantung)")
 * @param deskripsi   Deskripsi singkat panduan (misal: "Kompresi dada 100-120x/mnt • Golden period 4 mnt")
 * @param kategori    Badge label (misal: "Kritis", "P3K", "Bencana", "Evakuasi")
 * @param kategoriGroup Kategori filter chip ("P3K Medis", "Bencana Alam", "Evakuasi")
 * @param goldenPeriod Label batas waktu darurat (misal: "Golden Period: 4 Menit")
 * @param isiLengkap  Konten penjelasan umum
 * @param langkah     Daftar langkah pertolongan cepat berurutan
 * @param pantangan   Daftar hal yang jangan dilakukan
 */
data class PanduanItem(
    val judul: String,
    val deskripsi: String,
    val kategori: String,
    val kategoriGroup: String,
    val goldenPeriod: String = "Golden Period: 4 Menit",
    val isiLengkap: String = "",
    val langkah: List<Pair<String, String>> = emptyList(),
    val pantangan: List<String> = emptyList()
)
