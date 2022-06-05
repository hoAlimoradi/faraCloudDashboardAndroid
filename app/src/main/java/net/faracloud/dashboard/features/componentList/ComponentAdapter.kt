package net.faracloud.dashboard.features.componentList

import kotlinx.android.synthetic.main.row_component.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity

class ComponentAdapter(val callback: ComponentItemClickCallback) : BaseAdapter<ComponentAdapter.MarketViewHolder, ProviderRecycleViewViewRowEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_component,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: ProviderRecycleViewViewRowEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            componentCheckBox.text = model.title
            componentImageView.setOnClickListener {
                callback.onClicked(model)
            }
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    interface ComponentItemClickCallback {
        fun onClicked(item: ProviderRecycleViewViewRowEntity)
    }
}
