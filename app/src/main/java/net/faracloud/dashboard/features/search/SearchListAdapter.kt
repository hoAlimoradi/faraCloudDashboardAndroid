package net.faracloud.dashboard.features.search


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_search_list.view.*
import net.faracloud.dashboard.R
import java.util.*
import kotlin.collections.ArrayList

class SearchListAdapter(private var data: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var filterList = ArrayList<String>()

    lateinit var mContext: Context

  /*  class CountryHolder(var viewBinding: RecyclerviewRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root)*/

    class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    init {
        filterList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_search_list,parent,false)
        return CountryHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val countryHolder = holder as CountryHolder

        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            searchCheckBox.text = filterList[position]
        }
        holder.itemView.setOnClickListener {

            Log.d("Selected:", filterList[position])
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = data
                } else {
                    val resultList = ArrayList<String>()
                    for (row in data) {
                        if (row.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

}

















/*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_observation.view.*
import kotlinx.android.synthetic.main.row_search_list.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.extentions.loge
import java.util.*

//SearchRecycleViewViewRowEntity
class SearchListAdapter(private var data: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() ,
    Filterable {

    var filterList = ArrayList<String>()

    init {
        filterList = data
    }

    fun addAllData(items: ArrayList<String>) {
        this.data.addAll(items)
        filterList = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_search_list,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            searchCheckBox.text = data[position]
            */
/* valueTitleCounter.text = model.value
             timestampTitleCounter.text = model.timestamp
             latitudeCounter.text = model.latitude.toString()
             longitudeCounter.text = model.longitude.toString()
             timeTitleCounter.text = model.time*//*

        }
    }

    fun clear() {
        if (data.size > 0) {
            this.data.clear()
        }
        if (filterList.size > 0) {
            this.filterList.clear()
            this.notifyItemRangeRemoved(0,filterList.size - 1)
        }
    }


    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                loge(" performFiltering constraint.toString(): " + constraint.toString())
                val charSearch = constraint.toString()
                filterList.forEach {
                    loge(" ===========befor===========: "  )
                    loge(" filterList ->: " + it)
                    loge(" ======================: "  )
                }
                filterList.clear()
                if (charSearch.isEmpty()) {
                    filterList = data
                } else {
                    val resultList = ArrayList<String>()
                    for (row in data) {
                        */
/*if (row.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }*//*

                        if (row.contains(charSearch)) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }

                filterList.forEach {
                    loge(" ===========after===========: "  )
                    loge(" filterList ->: " + it)
                    loge(" ======================: "  )
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                loge(" publishResults constraint.toString(): " + constraint.toString())
                filterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }




}*/
