package com.example.project_prasetio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project_prasetio.R
import com.example.project_prasetio.databinding.ItemPanduanBinding
import com.example.project_prasetio.model.PanduanItem

/**
 * PanduanAdapter — RECYCLERVIEW CUSTOM ADAPTER untuk Panduan Tindakan (Screen 2).
 * Menerapkan ViewHolder pattern dengan ViewBinding (Pertemuan 10).
 */
class PanduanAdapter(
    private var dataList: List<PanduanItem>,
    private var onItemClick: ((PanduanItem) -> Unit)? = null
) : RecyclerView.Adapter<PanduanAdapter.ViewHolder>() {

    // Overload constructor untuk fleksibilitas kompatibilitas
    constructor(
        context: Context,
        dataList: List<PanduanItem>,
        onItemClick: ((PanduanItem) -> Unit)? = null
    ) : this(dataList, onItemClick)

    fun updateData(newList: List<PanduanItem>) {
        this.dataList = newList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (PanduanItem) -> Unit) {
        this.onItemClick = listener
    }

    fun getItem(position: Int): PanduanItem = dataList[position]

    inner class ViewHolder(val binding: ItemPanduanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PanduanItem) {
            val context = binding.root.context

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

            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPanduanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}
