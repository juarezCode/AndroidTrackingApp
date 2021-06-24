package com.juarez.trackingmapapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juarez.trackingmapapp.databinding.ItemLocationBinding
import com.juarez.trackingmapapp.model.MapLocation

class LocationsAdapter(
    private val locations: ArrayList<MapLocation>,
    private val onClick: (MapLocation, String) -> Unit,
) :
    RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    fun update(newUsers: List<MapLocation>) {
        locations.clear()
        locations.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(locations[position]) {
                binding.txtItemLocation.text = routeName
                binding.btnDeteleLocation.setOnClickListener { onClick(this, "Delete") }
            }
            itemView.setOnClickListener { onClick(locations[position], "Click") }
        }
    }

    override fun getItemCount() = locations.size
}
