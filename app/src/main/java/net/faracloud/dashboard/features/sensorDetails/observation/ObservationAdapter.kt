package net.faracloud.dashboard.features.sensorDetails.observation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_observation.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter

class ObservationAdapter() : BaseAdapter<ObservationAdapter.MarketViewHolder, ObservationRecycleViewRowEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_observation,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: ObservationRecycleViewRowEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            valueTitleCounter.text = model.value
            timestampTitleCounter.text = model.timestamp
            latitudeCounter.text = model.latitude.toString()
            longitudeCounter.text = model.longitude.toString()
            timeTitleCounter.text = model.time
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }
}