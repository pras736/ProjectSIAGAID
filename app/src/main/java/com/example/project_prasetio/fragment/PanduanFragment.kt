package com.example.project_prasetio.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_prasetio.DetailActivity
import com.example.project_prasetio.R
import com.example.project_prasetio.adapter.PanduanAdapter
import com.example.project_prasetio.databinding.FragmentPanduanBinding
import com.example.project_prasetio.model.PanduanItem

/**
 * PanduanFragment — Tab kedua Bottom Navigation.
 *
 * Menampilkan daftar panduan darurat menggunakan:
 * - ListView dengan CUSTOM ADAPTER (PanduanAdapter)
 * - Data berupa List<PanduanItem> yang berisi panduan keselamatan SIAGA ID
 *
 * Saat item diklik, akan membuka DetailActivity dengan konten isi panduan lengkap.
 */
class PanduanFragment : Fragment() {

    private var _binding: FragmentPanduanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPanduanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Siapkan data list panduan darurat sesuai tema proyek SIAGA ID
        val dataPanduan = getDataPanduan()

        // Inisialisasi Custom Adapter dan pasang ke ListView
        val adapter = PanduanAdapter(requireContext(), dataPanduan)
        binding.listViewPanduan.adapter = adapter

        // Set listener klik item ListView
        binding.listViewPanduan.setOnItemClickListener { _, _, position, _ ->
            val item = dataPanduan[position]
            Toast.makeText(requireContext(), "Membuka: ${item.judul}", Toast.LENGTH_SHORT).show()

            // Buka DetailActivity dengan data panduan yang dipilih
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TITLE, item.judul)
                putExtra(DetailActivity.EXTRA_DESCRIPTION, item.isiLengkap)
                putExtra(DetailActivity.EXTRA_IMAGE_RES, R.drawable.img_darurat)
            }
            startActivity(intent)
        }
    }

    /**
     * Menyediakan data statis daftar Panduan Darurat SIAGA ID.
     * Setiap item memiliki: emoji, judul, deskripsi singkat, kategori, dan isi lengkap.
     */
    private fun getDataPanduan(): List<PanduanItem> {
        return listOf(
            PanduanItem(
                emoji = "❤️",
                judul = "CPR & Resusitasi Jantung Paru",
                deskripsi = "Kompresi dada darurat · Golden period henti napas",
                kategori = "Jantung",
                isiLengkap = """
                    PANDUAN CPR & RESUSITASI JANTUNG PARU (RJP)
                    
                    Langkah Pertolongan Pertama:
                    
                    1. CEK RESPON KORBAN
                    Tepuk bahu dan panggil korban dengan suara keras.
                    
                    2. PANGGIL BANTUAN DARURAT
                    Telepon 118/119 atau minta orang sekitar memanggil ambulans.
                    
                    3. POSISI KORBAN
                    Baringkan korban di permukaan keras dan rata.
                    
                    4. KOMPRESI DADA (30 kali)
                    - Tumit tangan di tengah dada, antara puting susu.
                    - Kedua tangan dikaitkan, lengan lurus.
                    - Tekan sedalam 5-6 cm, ritme 100-120x/menit.
                    
                    5. BANTUAN NAPAS (2 kali)
                    - Tengadahkan kepala, angkat dagu.
                    - Tutup hidung korban, tiupkan udara 1 detik.
                    - Ulangi 30:2 hingga bantuan tiba.
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "🩸",
                judul = "Menghentikan Pendarahan",
                deskripsi = "Teknik penekanan luka · Penanganan luka terbuka",
                kategori = "Luka",
                isiLengkap = """
                    PANDUAN MENGHENTIKAN PENDARAHAN
                    
                    1. KENAKAN PELINDUNG
                    Gunakan sarung tangan atau kantong plastik bersih.
                    
                    2. TEKAN LANGSUNG LUKA
                    Gunakan kain bersih/perban steril, tekan kuat minimal 10 menit.
                    
                    3. JANGAN ANGKAT TEKANAN
                    Jika kain basah darah, tambahkan kain baru di atasnya — jangan ganti.
                    
                    4. TINGGIKAN BAGIAN TERLUKA
                    Angkat anggota tubuh yang terluka di atas level jantung.
                    
                    5. TORNIKET (Darurat Ekstrem)
                    Pasang 5-7 cm di atas luka, kencangkan hingga pendarahan berhenti.
                    Catat jam pemasangan. Segera evakuasi ke RS.
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "🔥",
                judul = "Penanganan Luka Bakar",
                deskripsi = "Pertolongan pertama luka bakar ringan hingga berat",
                kategori = "Luka Bakar",
                isiLengkap = """
                    PANDUAN PENANGANAN LUKA BAKAR
                    
                    LUKA BAKAR RINGAN (Grade 1-2):
                    1. Jauhkan dari sumber panas segera.
                    2. Siram dengan air mengalir dingin 10-20 menit.
                    3. JANGAN gunakan es, mentega, atau odol.
                    4. Tutup dengan kain bersih atau perban non-lengket.
                    5. Minum pereda nyeri jika perlu.
                    
                    LUKA BAKAR BERAT (Grade 3):
                    1. Panggil 118/119 segera.
                    2. JANGAN copot pakaian yang menempel pada luka.
                    3. Tutup dengan kain bersih lembab.
                    4. Jaga korban tetap hangat, cegah shock.
                    
                    HINDARI: Es, pasta gigi, minyak, dan pecahkan gelembung kulit.
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "🌊",
                judul = "Penanganan Tenggelam",
                deskripsi = "Penyelamatan korban tenggelam & CPR pasca tenggelam",
                kategori = "Bencana",
                isiLengkap = """
                    PANDUAN PENANGANAN KORBAN TENGGELAM
                    
                    DI LOKASI AIR:
                    1. Hubungi 118/119 segera.
                    2. Lempar tali atau benda apung — JANGAN terjun langsung kecuali terlatih.
                    3. Tarik korban ke darat dengan posisi wajah menghadap atas.
                    
                    DI DARAT:
                    1. CEK RESPON — panggil nama korban.
                    2. Jika tidak bernapas: mulai CPR 30:2 segera.
                    3. Jangan buang waktu mencoba keluarkan air dari paru-paru.
                    4. Lanjutkan CPR hingga tim medis tiba atau korban bernapas.
                    5. Jika sadar: posisikan miring (posisi pemulihan), selimuti dari dingin.
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "⚡",
                judul = "Sengatan Listrik",
                deskripsi = "Keselamatan & pertolongan pertama korban setrum",
                kategori = "Kelistrikan",
                isiLengkap = """
                    PANDUAN PERTOLONGAN KORBAN SENGATAN LISTRIK
                    
                    PERHATIAN: JANGAN sentuh korban sebelum sumber listrik dimatikan!
                    
                    1. MATIKAN ALIRAN LISTRIK
                    Cabut steker, matikan MCB/sekring, atau putuskan sumber listrik.
                    
                    2. JAUHKAN KORBAN DARI SUMBER LISTRIK
                    Gunakan benda non-konduktor (kayu kering, plastik) untuk mendorong.
                    
                    3. CEK KONDISI KORBAN
                    Periksa pernapasan dan denyut nadi. Hubungi 118/119.
                    
                    4. LAKUKAN CPR JIKA PERLU
                    Jika tidak sadar dan tidak bernapas, mulai CPR 30:2.
                    
                    5. TANGANI LUKA BAKAR
                    Tutup luka bakar dengan kain bersih, jangan gunakan air es.
                    
                    6. CEGAH SHOCK
                    Baringkan korban, naikkan kaki, selimuti, tunggu paramedis.
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "🌋",
                judul = "Gempa Bumi",
                deskripsi = "Prosedur DROP-COVER-HOLD saat dan pasca gempa",
                kategori = "Bencana",
                isiLengkap = """
                    PANDUAN KESELAMATAN GEMPA BUMI
                    
                    SAAT GEMPA (Drop - Cover - Hold On):
                    
                    DALAM RUANGAN:
                    1. DROP: Berlutut dan lindungi kepala & leher dengan tangan.
                    2. COVER: Masuk ke bawah meja yang kokoh atau susuri tembok.
                    3. HOLD ON: Pegang erat hingga gempa berhenti.
                    4. JAUHI: Jendela, lemari, lampu gantung, dan benda berat.
                    
                    DI LUAR RUANGAN:
                    1. Jauhi gedung, pohon, tiang listrik.
                    2. Jongkok di tempat terbuka, lindungi kepala.
                    
                    SETELAH GEMPA:
                    1. Periksa kondisi diri dan sekitar.
                    2. Waspada gempa susulan.
                    3. Evakuasi dari gedung melalui tangga (bukan lift).
                    4. Jauhi pantai (waspadai tsunami).
                    5. Dengarkan arahan BNPB/BPBD.
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "🐍",
                judul = "Gigitan Ular Berbisa",
                deskripsi = "Pertolongan pertama gigitan ular & penanganan bisa",
                kategori = "Racun",
                isiLengkap = """
                    PANDUAN PENANGANAN GIGITAN ULAR BERBISA
                    
                    1. TENANGKAN KORBAN
                    Hindari gerakan berlebihan — memperlambat penyebaran bisa.
                    
                    2. IMOBILISASI ANGGOTA TUBUH
                    Bidai bagian yang digigit seperti patah tulang.
                    Posisikan di bawah level jantung.
                    
                    3. LEPAS PERHIASAN & PAKAIAN KETAT
                    Di sekitar area gigitan untuk mencegah pembengkakan.
                    
                    4. BERSIHKAN LUKA
                    Cuci dengan air sabun mengalir.
                    
                    5. SEGERA KE RS / HUBUNGI 118
                    Catat warna, ukuran, dan pola ular jika memungkinkan.
                    
                    JANGAN LAKUKAN:
                    - Mengisap bisa dengan mulut
                    - Memasang torniket
                    - Memotong dan mengeluarkan darah di luka
                    - Memberikan alkohol atau obat tanpa resep dokter
                """.trimIndent()
            ),
            PanduanItem(
                emoji = "🥵",
                judul = "Heat Stroke (Kelelahan Panas)",
                deskripsi = "Penanganan pingsan & heat stroke saat cuaca panas",
                kategori = "Cuaca",
                isiLengkap = """
                    PANDUAN PENANGANAN HEAT STROKE
                    
                    TANDA-TANDA HEAT STROKE:
                    - Suhu tubuh > 40°C
                    - Kulit merah, panas, dan kering (tidak berkeringat)
                    - Bingung, pingsan, atau kejang
                    - Denyut nadi cepat dan kuat
                    
                    PERTOLONGAN PERTAMA:
                    
                    1. PINDAHKAN KE TEMPAT SEJUK
                    Teduh, ber-AC, atau berangin.
                    
                    2. TURUNKAN SUHU TUBUH SEGERA
                    - Kompres es di leher, ketiak, dan selangkangan.
                    - Siram tubuh dengan air dingin.
                    - Kipasi terus-menerus.
                    
                    3. BERIKAN MINUM (Jika Sadar)
                    Air putih atau minuman elektrolit, sedikit-sedikit.
                    
                    4. HUBUNGI 118/119
                    Heat stroke adalah kondisi darurat medis!
                    
                    5. JANGAN BERI OBAT PENURUN PANAS
                    (seperti paracetamol) — tidak efektif untuk heat stroke.
                """.trimIndent()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
