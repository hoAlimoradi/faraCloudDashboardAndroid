package net.faracloud.dashboard.features.tenant.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_tenant.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter
import net.faracloud.dashboard.core.database.TenantEntity

class TenantAdapter(val callback: TenantItemClickCallback) : BaseAdapter<TenantAdapter.MarketViewHolder, TenantEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_tenant,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: TenantEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            tenantTitle.text = model.tenantName

            tenantImageView.setOnClickListener {
                callback.onClicked(model, TenantItemClickCallbackType.DELETE)
            }

        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    interface TenantItemClickCallback {
        fun onClicked(item: TenantEntity, type: TenantItemClickCallbackType)
    }

    enum class TenantItemClickCallbackType {
        MORE,
        EDIT,
        DELETE
    }
}


