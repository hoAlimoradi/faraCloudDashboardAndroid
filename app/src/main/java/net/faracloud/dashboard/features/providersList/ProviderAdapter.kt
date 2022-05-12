package net.faracloud.dashboard.features.providersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_provider.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter

class ProviderAdapter() : BaseAdapter<ProviderAdapter.MarketViewHolder, ProviderRecycleViewViewRowEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_provider,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder,model: ProviderRecycleViewViewRowEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
//        params.bottomMargin = if (data.lastIndex == position) holder.itemView.resources.getDimension(R.dimen.space_bottom_navigation).toInt() else 0
//        holder.itemView.layoutParams = params
        with(holder.itemView) {
            providerCheckBox.text = model.title
        }
    }


    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }
}


