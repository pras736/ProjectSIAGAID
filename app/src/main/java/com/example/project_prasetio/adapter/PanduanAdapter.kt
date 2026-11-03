package com.example.project_prasetio.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.project_prasetio.R
import com.example.project_prasetio.databinding.ItemPanduanBinding
import com.example.project_prasetio.model.PanduanItem

/**
 * PanduanAdapter — CUSTOM ADAPTER untuk ListView Panduan Tindakan (Screen 2).
 * Menerapkan ViewHolder pattern untuk performa optimal.
 */
class PanduanAdapter(
    private val context: Context,
    private var dataList: List<PanduanItem>
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    fun updateData(newList: List<PanduanItem>) {
        this.dataList = newList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemPanduanBinding
        val view: View

        if (convertView == null) {
            binding = ItemPanduanBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemPanduanBinding
        }

        val item = dataList[position]

        binding.tvJudulPanduan.text = item.judul
        binding.tvDeskripsiPanduan.text = item.deskripsi
        binding.tvBadgeKategori.text = item.kategori

        when (item.kategori) {
            "Kritis" -> {
                binding.viewIndicatorStrip.setBackgroundResource(R.drawable.bg_badge_red)
                binding.tvBadgeKategori.setBackgroundResource(R.drawable.bg_pill_kritis)
                binding.tvBadgeKategori.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            "P3K" -> {
                binding.viewIndicatorStrip.setBackgroundResource(R.drawable.bg_badge_orange)
                binding.tvBadgeKategori.setBackgroundResource(R.drawable.bg_pill_p3k)
                binding.tvBadgeKategori.setTextColor(ContextCompat.getColor(context, R.color.orange_dark))
            }
            "Bencana" -> {
                binding.viewIndicatorStrip.setBackgroundResource(R.drawable.bg_badge_orange)
                binding.tvBadgeKategori.setBackgroundResource(R.drawable.bg_pill_bencana)
                binding.tvBadgeKategori.setTextColor(ContextCompat.getColor(context, R.color.navy))
            }
            "Evakuasi" -> {
                binding.viewIndicatorStrip.setBackgroundResource(R.drawable.bg_badge_orange)
                binding.tvBadgeKategori.setBackgroundResource(R.drawable.bg_pill_bencana)
                binding.tvBadgeKategori.setTextColor(ContextCompat.getColor(context, R.color.navy))
            }
            else -> {
                binding.viewIndicatorStrip.setBackgroundResource(R.drawable.bg_badge_orange)
                binding.tvBadgeKategori.setBackgroundResource(R.drawable.bg_pill_p3k)
                binding.tvBadgeKategori.setTextColor(ContextCompat.getColor(context, R.color.orange_dark))
            }
        }

        return view
    }
}
