package net.faracloud.dashboard.features.map.presentation

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.carto.styles.*
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
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.internal.utils.BitmapUtils
import org.neshan.mapsdk.model.Marker
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
class MapFragment : BuilderFragment<MapState, MapViewModel>() , MapView.OnMarkerClickListener{

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

        providersButton.setOnClickListener {
          viewModel.navigateToSetting()
        }
        settingsImageButton.setOnClickListener {
            viewModel.navigateToDetail()
        }
        searchImageButton.setOnClickListener {
            viewModel.navigateToSearch()
        }

        //set map focus position
       // map.isTrafficEnabled = true //  .setFocalPointPosition(, 0f)
        map.setZoom(14.5f, 0f)
        val animSt = AnimationStyleBuilder()
            .let {
                it.fadeAnimationType = AnimationType.ANIMATION_TYPE_SMOOTHSTEP
                it.sizeAnimationType = AnimationType.ANIMATION_TYPE_SPRING
                it.phaseInDuration = 0.5f
                it.phaseOutDuration = 0.5f
                it.buildStyle()
            }

        val st = MarkerStyleBuilder()
            .let {
                it.size = 48f
                it.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
                    BitmapFactory.decodeResource(resources, R.drawable.ic_marker))
                it.animationStyle = animSt
                it.buildStyle()
            }
        val markerLocation = LatLng(35.69344445206249, 51.34592769893889)
        val marker = Marker(markerLocation, st)

        marker.title = "testtt"
        marker.description = "aaaaaaaaaa"

        map.addMarker(marker)

        map.setOnMapClickListener{
            loge("it " +it.toString())
        }

        map.moveCamera(markerLocation, 1F)
        getComponentsFromDataBase()
    }



    private fun initMarker(modelList: List<ModelMain>) {
        /*
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
         */

    }

    override fun onResume() {
        super.onResume()

       /*

       Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        val x: TilesOverlay = map.overlayManager.tilesOverlay
        map.overlayManager.tilesOverlay.loadingBackgroundColor = android.R.color.black
        map.overlayManager.tilesOverlay.loadingLineColor = Color.argb(255,0,255,0)


       * */
        getComponentsFromDataBase()
    }

    private fun getComponentsFromDataBase() {
        viewModel.getComponentsFromDataBase().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->

                    /*
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
                     */

                }
            }
        }
    }

    private fun showComponentOnMap(modelList: List<ComponentEntity>) {

        /*
        for (i in modelList.indices) {
            overlayItem = ArrayList()
            overlayItem.add(
                OverlayItem(
                    modelList[i].nameComponent,
                    modelList[i].providerId,
                    GeoPoint(
                        modelList[i].latitude,
                        modelList[i].longitude
                    )
                )
            )
            val info = ModelMain()
            info.strName = modelList[i].nameComponent.toString()
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
         */


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

    override fun OnMarkerClicked(p0: Marker?) {
        loge("p0 " + p0?.title)
    }
}


