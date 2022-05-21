package net.faracloud.dashboard.features.sensorDetails.information

import net.faracloud.dashboard.features.sensorDetails.observation.ObservationRecycleViewRowEntity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_information.view.*
import kotlinx.android.synthetic.main.row_observation.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter

class InformationAdapter() : BaseAdapter<InformationAdapter.MarketViewHolder, String>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_information,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: String, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            informationValue.text = model
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }
}