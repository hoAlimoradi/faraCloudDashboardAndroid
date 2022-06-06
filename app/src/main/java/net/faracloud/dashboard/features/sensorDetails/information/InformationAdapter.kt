package net.faracloud.dashboard.features.sensorDetails.information

import net.faracloud.dashboard.features.sensorDetails.observation.ObservationRecycleViewRowEntity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.row_information.view.*
import kotlinx.android.synthetic.main.row_observation.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BaseAdapter
import net.faracloud.dashboard.features.sensorDetails.chart.ChartsAdapter
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView

class InformationAdapter(private val informations: List<String>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_MAP = 1
    }
    /*override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_information,parent,false)
        return MarketViewHolder(view)
    }*/

    class  TitleViewHolder(itemView: View) : ViewHolder(itemView) {
        //val cubicLinesTitle: TextView = itemView.findViewById(R.id.valueTitle)
        val title: TextView = itemView.findViewById(R.id.informationTitle)
        val value: TextView = itemView.findViewById(R.id.informationValue)
    }

    class  MapViewHolder(itemView: View) : ViewHolder(itemView) {
        val map: MapView = itemView.findViewById(R.id.mapView)
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12 -> InformationAdapter.TYPE_TITLE
            4  -> InformationAdapter.TYPE_MAP
            else -> throw IllegalArgumentException()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        InformationAdapter.TYPE_TITLE -> InformationAdapter.TitleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_information, parent, false)
        )

        InformationAdapter.TYPE_MAP -> InformationAdapter.MapViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_information_map, parent, false)
        )
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            InformationAdapter.TYPE_TITLE -> onBindTitle(holder, position)
            InformationAdapter.TYPE_MAP -> onBindMap(holder)
            else -> throw IllegalArgumentException()

        }
    private fun onBindTitle(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as InformationAdapter.TitleViewHolder
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            when (position) {
                0 -> {
                    informationTitle.text = "sensor"
                }
                1 -> {
                    informationTitle.text = "dataType"
                }
                2 -> {
                    informationTitle.text = "latitude "
                }

                3 -> {
                    informationTitle.text = "longitude"
                }

                4 -> {
                    // show map
                }

                5 -> {
                    informationTitle.text = "state"
                }

                6 -> {
                    informationTitle.text = "type"
                }

                7 -> {
                    informationTitle.text = "unit"
                }

                8 -> {
                    informationTitle.text = "timeZone"
                }

                9 -> {
                    informationTitle.text = "publicAccess"
                }
                10 -> {
                    informationTitle.text = "state"
                }

                11 -> {
                    informationTitle.text = "createdAt"
                }
                12 -> {
                    informationTitle.text = "updatedAt"
                }

                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
            informationValue.text = informations[position]
        }
        informations
    }
    private fun onBindMap(holder: RecyclerView.ViewHolder) {
        val viewHolder = holder as InformationAdapter.MapViewHolder
        // circularProgressViewHolder.circularProgressTitle.text = "circularProgressTitle"
        val sensorLocationArray = informations[4].split(" ").toTypedArray()
        val latitude: Double = sensorLocationArray[0].toDouble()
        val longitude: Double = sensorLocationArray[1].toDouble()
        val geoPoint = GeoPoint(latitude, longitude)
        val marker = org.osmdroid.views.overlay.Marker(viewHolder.map)
        marker.icon = ContextCompat.getDrawable(viewHolder.map.context, R.drawable.ic_place)
        marker.position = geoPoint
        viewHolder.map.overlays.add(marker)

        viewHolder.map.setMultiTouchControls(false)
        viewHolder.map.controller.animateTo(geoPoint)
        viewHolder.map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        viewHolder.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        val mapController = viewHolder.map.controller as MapController
        mapController.setCenter(geoPoint)
        mapController.zoomTo(15)
        viewHolder.map.invalidate()


    }

    override fun getItemCount(): Int {
         return 13
    }
    /*
    override fun onBindHolder(holder: MarketViewHolder, model: String, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        with(holder.itemView) {
            when (position) {
                0 -> {
                    informationTitle.text = "sensor"
                }
                1 -> {
                    informationTitle.text = "dataType"
                }
                2 -> {
                    informationTitle.text = "latitude "
                }

                3 -> {
                    informationTitle.text = "longitude"
                }

                4 -> {
                    // show map
                }

                5 -> {
                    informationTitle.text = "state"
                }

                6 -> {
                    informationTitle.text = "type"
                }

                7 -> {
                    informationTitle.text = "unit"
                }

                8 -> {
                    informationTitle.text = "timeZone"
                }

                9 -> {
                    informationTitle.text = "publicAccess"
                }
                10 -> {
                    informationTitle.text = "state"
                }

                11 -> {
                    informationTitle.text = "createdAt"
                }
                12 -> {
                    informationTitle.text = "updatedAt"
                }

                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
            informationValue.text = model
        }
    }

    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }*/
}