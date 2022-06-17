package net.faracloud.dashboard.features.map.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import net.faracloud.dashboard.BuildConfig
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.BundleKeys
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.TilesOverlay
import java.io.IOException
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class MapFragment : BuilderFragment<MapState, MapViewModel>() {

    var modelMainList: MutableList<ModelMain> = ArrayList()
    lateinit var mapController: MapController
    lateinit var overlayItem: ArrayList<OverlayItem>

    private val viewModel: MapViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<MapState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        val x: TilesOverlay = map.overlayManager.tilesOverlay
        map.overlayManager.tilesOverlay.loadingBackgroundColor = android.R.color.black
        map.overlayManager.tilesOverlay.loadingLineColor = Color.argb(255,0,255,0)
        providersButton.setOnClickListener {
          viewModel.navigateToSetting()
        }
        settingsImageButton.setOnClickListener {
            viewModel.navigateToDetail()
        }
        searchImageButton.setOnClickListener {
            viewModel.navigateToSearch()
        }

       // getLocationMarker()
      /*
         val geoPoint = GeoPoint(-6.3035467, 106.8693513)
        map.setMultiTouchControls(true)
        map.controller.animateTo(geoPoint)
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        mapController = map.controller as MapController
        mapController.setCenter(geoPoint)
        mapController.zoomTo(15)

        */
        getComponentsFromDataBase()
    }

    //get lat long
    private fun getLocationMarker() {
        try {

            //val stream = this.context!!.assets.open("sample_maps.json")
            val stream = map.context.assets.open("sample_maps.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strContent = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonObject = JSONObject(strContent)
                val jsonArrayResult = jsonObject.getJSONArray("results")
                for (i in 0 until jsonArrayResult.length()) {
                    val jsonObjectResult = jsonArrayResult.getJSONObject(i)
                    val modelMain = ModelMain()
                    modelMain.strName = jsonObjectResult.getString("name")
                    modelMain.strVicinity = jsonObjectResult.getString("vicinity")

                    //get lat long
                    val jsonObjectGeo = jsonObjectResult.getJSONObject("geometry")
                    val jsonObjectLoc = jsonObjectGeo.getJSONObject("location")
                    modelMain.latLoc = jsonObjectLoc.getDouble("lat")
                    modelMain.longLoc = jsonObjectLoc.getDouble("lng")
                    modelMainList.add(modelMain)
                }
                initMarker(modelMainList)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
            /*Toast.makeText(
                this@MapFragment.context,
                "Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.",
                Toast.LENGTH_SHORT
            ).show()*/
        }
    }

    private fun initMarker(modelList: List<ModelMain>) {
        for (i in modelList.indices) {
            overlayItem = ArrayList()
            overlayItem.add(
                OverlayItem(
                    modelList[i].strName, modelList[i].strVicinity, GeoPoint(
                        modelList[i].latLoc, modelList[i].longLoc
                    )
                )
            )
            val info = ModelMain()
            info.strName = modelList[i].strName
            info.strVicinity = modelList[i].strVicinity

            val marker = org.osmdroid.views.overlay.Marker(map)
            //marker.icon = map.context.resources.getDrawable(R.drawable.ic_place)
            this.activity?.let {

                marker.icon = ContextCompat.getDrawable(it, R.drawable.ic_place)
            }
            marker.position = GeoPoint(modelList[i].latLoc, modelList[i].longLoc)
            marker.relatedObject = info
            marker.infoWindow = CustomInfoWindow(map)
            marker.setOnMarkerClickListener { item, arg1 ->
                item.showInfoWindow()
                true
            }

            map.overlays.add(marker)
            map.invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
       /* Configuration.getInstance().load(this.context, PreferenceManager.getDefaultSharedPreferences(this.context))
        if (map != null) {
            map.onResume()
        }*/
        getComponentsFromDataBase()
    }

    private fun getComponentsFromDataBase() {
        viewModel.getComponentsFromDataBase().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->

                    if(list.isNullOrEmpty()) {
                        val geoPoint = GeoPoint(35.695706, 51.400060)
                        map.setMultiTouchControls(true)
                        map.controller.animateTo(geoPoint)
                        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

                        mapController = map.controller as MapController
                        mapController.setCenter(geoPoint)
                        mapController.zoomTo(15)
                        map.invalidate()
                    } else {
                        val firstComponent = list.first()
                        val geoPoint = GeoPoint(firstComponent.latitude, firstComponent.longitude)
                        map.setMultiTouchControls(true)
                        map.controller.animateTo(geoPoint)
                        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

                        mapController = map.controller as MapController
                        mapController.setCenter(geoPoint)
                        mapController.zoomTo(15)

                        showComponentOnMap(list)
                    }
                }
            }
        }
    }

    private fun showComponentOnMap(modelList: List<ComponentEntity>) {

        for (i in modelList.indices) {
            overlayItem = ArrayList()
            overlayItem.add(
                OverlayItem(
                    modelList[i].name,
                    modelList[i].providerId,
                    GeoPoint(
                        modelList[i].latitude,
                        modelList[i].longitude
                    )
                )
            )
            val info = ModelMain()
            info.strName = modelList[i].name.toString()
            info.strVicinity = modelList[i].type.toString()

            val marker = org.osmdroid.views.overlay.Marker(map)
            //marker.icon = map.context.resources.getDrawable(R.drawable.ic_place)
            this.activity?.let {
                marker.icon = ContextCompat.getDrawable(it, R.drawable.ic_place)
            }
            marker.position = GeoPoint(modelList[i].latitude, modelList[i].longitude)
            marker.relatedObject = info
            marker.infoWindow = CustomInfoWindow(map)
            marker.setOnMarkerClickListener { item, arg1 ->
                //item.showInfoWindow()
                //getFindViewController()?.navigateUp()
                val bundle = Bundle()
                bundle.putBoolean(BundleKeys.startFromMap,true)
                getFindViewController()?.navigate(R.id.navigateFromMapToSensorListFragment, bundle)
                true
            }

            map.overlays.add(marker)
            map.invalidate()
        }

    }


    override fun onStateChange(state: MapState) {
        when (state) {
            MapState.IDLE -> {
                loge("IDLE")
                getComponentsFromDataBase()
            }
            MapState.START_DETAIL -> {
                loge("START_DETAIL")
                map.invalidate()
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromMapToSettingFragment)

            }
            MapState.START_SETTING -> {
                loge("FORCE_UPDATE")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromMapToProviderListFragment)
            }
            MapState.START_SEARCH -> {
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromMapToSearchFragment)
            }

        }
    }
}


