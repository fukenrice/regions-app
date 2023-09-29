package com.example.regions_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.regions_app.R
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.databinding.RegionListItemBinding
import com.squareup.picasso.Picasso

class RegionsAdapter(
    private val onItemClicked: (region: RegionModel) -> Unit,
    private val data: MutableList<RegionModel>,
    private var likes: MutableMap<String, Boolean>
) : RecyclerView.Adapter<RegionsAdapter.DataViewHolder>() {

    class DataViewHolder(
        private val onItemClicked: (region: RegionModel) -> Unit,
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(region: RegionModel, likes: MutableMap<String, Boolean>) {
            val binding = RegionListItemBinding.bind(itemView)
            Picasso.get().load(region.thumbUrls[0]).into(binding.ivRegionImage)
            binding.tvRegionName.text = region.title
            if (likes[region.title] == true) {
                binding.ivRegionLiked.setImageResource(R.drawable.ic_heart_on)
            }
            itemView.setOnClickListener { onItemClicked(region) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            onItemClicked,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.region_list_item, parent, false)
        )

    override fun getItemCount(): Int = data.count()
    
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(data[position], likes)

    fun clear() = data.clear()

    fun addData(regions: List<RegionModel>) = this.data.addAll(regions)

    fun updateLikes(likes: MutableMap<String, Boolean>) {
        this.likes.clear()
        this.likes.putAll(likes)
    }
}