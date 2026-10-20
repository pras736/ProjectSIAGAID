package com.example.project_prasetio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.project_prasetio.R
import com.example.project_prasetio.model.PanduanItem

/**
 * Custom ArrayAdapter untuk menampilkan daftar Panduan Darurat di ListView.
 *
 * Adapter ini menggunakan View Holder Pattern untuk performa yang optimal,
 * serta me-render item_panduan.xml sebagai tampilan tiap baris list.
 *
 * @param context   Context dari Fragment/Activity pemanggil
 * @param dataList  List data PanduanItem yang akan ditampilkan
 */
class PanduanAdapter(
    context: Context,
    private val dataList: List<PanduanItem>
) : ArrayAdapter<PanduanItem>(context, 0, dataList) {

    /**
     * View Holder Pattern — menyimpan referensi view agar tidak perlu
     * findViewById() berulang kali saat scroll (meningkatkan performa ListView).
     */
    private class ViewHolder(view: View) {
        val tvIconEmoji: TextView = view.findViewById(R.id.tvIconEmoji)
        val tvJudulPanduan: TextView = view.findViewById(R.id.tvJudulPanduan)
        val tvDeskripsiPanduan: TextView = view.findViewById(R.id.tvDeskripsiPanduan)
        val tvBadgeKategori: TextView = view.findViewById(R.id.tvBadgeKategori)
    }

    /**
     * getView() dipanggil oleh ListView setiap kali satu baris perlu di-render.
     *
     * Logika inflate hanya berjalan jika convertView null (baris baru),
     * sedangkan baris yang di-recycle akan langsung menggunakan tag ViewHolder.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val rowView: View

        if (convertView == null) {
            // Inflate layout custom item panduan
            rowView = LayoutInflater.from(context).inflate(R.layout.item_panduan, parent, false)
            // Buat dan simpan ViewHolder ke tag view
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            // Ambil kembali ViewHolder yang sudah ada (view di-recycle)
            rowView = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        // Ambil data sesuai posisi
        val item = dataList[position]

        // Bind data ke tiap komponen View
        viewHolder.tvIconEmoji.text = item.emoji
        viewHolder.tvJudulPanduan.text = item.judul
        viewHolder.tvDeskripsiPanduan.text = item.deskripsi
        viewHolder.tvBadgeKategori.text = item.kategori

        return rowView
    }
}
