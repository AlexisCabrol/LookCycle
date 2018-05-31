package fr.cabrolalexis.velo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.base.BaseFragment
import fr.cabrolalexis.velo.viewmodel.CityDetailsViewModel
import kotlinx.android.synthetic.main.fragment_maps.view.*
import org.kodein.di.direct
import org.kodein.di.generic.instance


class MapsFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var viewModel: CityDetailsViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var mView : View

    companion object {
        fun newInstance(): MapsFragment = MapsFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_maps, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = kodein.direct.instance(arg = activity)

        mapView = mView.map
        if(mapView != null) {
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val stationList = viewModel.station.value
        MapsInitializer.initialize(context)

        googleMap = p0
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        if(stationList != null && !stationList.isEmpty()) {
            for(station in stationList) {
                googleMap.addMarker(MarkerOptions().position(LatLng(station.position.lat, station.position.lng)).title(station.name))
            }
            val cameraPosition = CameraPosition.builder().target(LatLng(stationList.get(0).position.lat,
                    stationList.get(0).position.lng)).zoom(15f).build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


        }



    }

}