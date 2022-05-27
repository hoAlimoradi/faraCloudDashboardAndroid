package net.faracloud.dashboard.features.statistics



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_statistics.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter

class StatisticsAdapter() : BaseAdapter<StatisticsAdapter.MarketViewHolder, StatisticsRecycleViewViewRowEntity>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_statistics,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder, model: StatisticsRecycleViewViewRowEntity, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            title.text = model.title
            titleCounter.text = model.titleCounter
            subTitle.text = model.subTitle
            description.text = model.description
            descriptionCounter.text = model.descriptionCounter
            secondDescription.text = model.secondDescription
            secondDescriptionCounter.text = model.secondDescriptionCounter
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }


}
