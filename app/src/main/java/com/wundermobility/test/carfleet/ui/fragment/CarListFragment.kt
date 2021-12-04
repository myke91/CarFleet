package com.wundermobility.test.carfleet.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.wundermobility.test.carfleet.R
import com.wundermobility.test.carfleet.databinding.FragmentCarListBinding
import com.wundermobility.test.carfleet.model.Car
import com.wundermobility.test.carfleet.ui.viewmodel.CarListViewModel
import kotlin.math.roundToInt


class CarListFragment : BaseFragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var viewModel: CarListViewModel
    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!
    private val markers = mutableMapOf<Marker?, Car>()
    private var isMarkersRemoved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()

        isMarkersRemoved = false
        viewModel.carList.observe(viewLifecycleOwner, Observer { cars ->
            binding.mapView.getMapAsync { map ->
                map.setOnMarkerClickListener(this);
                val GERMANY = LatLngBounds(
                    LatLng(47.644173, 7.552175),
                    LatLng(54.533735, 13.529632)
                )

                val width = resources.displayMetrics.widthPixels
                val height = resources.displayMetrics.heightPixels
                val padding = (width * 0.12).roundToInt()


                map.moveCamera(CameraUpdateFactory.newLatLngBounds(GERMANY, width, height, padding))
                if (cars.isNotEmpty()) {
                    cars.forEach {
                        markers.put(
                            map.addMarker(
                                MarkerOptions()
                                    .visible(true)
                                    .position(LatLng(it.lat, it.lon))
                                    .title(it.title)
                            ), it
                        )
                    }
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            showDialog(it)
        })
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (!isMarkersRemoved) {
            markers.forEach {
                if (it.key != marker) {
                    it.key?.remove()
                }
            }
            isMarkersRemoved = true
        } else {
            openCarDetailView(markers[marker])
        }
        return false
    }

    private fun openCarDetailView(car: Car?) {
        val bundle = Bundle()
        bundle.putString("carId", car?.carId.toString())

        val nextFragment = CarDetailFragment()
        nextFragment.setArguments(bundle)

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, nextFragment)
            ?.addToBackStack(null)?.commit()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CarListFragment()
    }
}
