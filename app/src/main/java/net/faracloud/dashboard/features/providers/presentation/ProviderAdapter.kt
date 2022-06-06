package net.faracloud.dashboard.features.providers.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_provider.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter

class ProviderAdapter(val callback: ProviderItemClickCallback) : BaseAdapter<ProviderAdapter.MarketViewHolder, ProviderRecycleViewViewRowEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_provider,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: ProviderRecycleViewViewRowEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            providerCheckBox.text = model.title
            moreTextView.setOnClickListener {
                callback.onClicked(model, ProviderItemClickCallbackType.MORE)
            }

            deleteImageView.setOnClickListener {
                callback.onClicked(model, ProviderItemClickCallbackType.DELETE)
            }

            editImageView.setOnClickListener {
                callback.onClicked(model, ProviderItemClickCallbackType.EDIT)
            }
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    interface ProviderItemClickCallback {
        fun onClicked(item: ProviderRecycleViewViewRowEntity, type: ProviderItemClickCallbackType)
    }

    enum class ProviderItemClickCallbackType {
        MORE,
        EDIT,
        DELETE
    }
}


