package net.faracloud.dashboard.features.sensorsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_sensor.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter
import net.faracloud.dashboard.features.tenant.providersList.ProviderRecycleViewViewRowEntity

class SensorAdapter(val callback: SensorItemClickCallback) : BaseAdapter<SensorAdapter.MarketViewHolder, ProviderRecycleViewViewRowEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_sensor,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: ProviderRecycleViewViewRowEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            sensorCheckBox.text = model.title
            sensorImageView.setOnClickListener {
                callback.onClicked(model)
            }
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    interface SensorItemClickCallback {
        fun onClicked(item: ProviderRecycleViewViewRowEntity)
    }
}
