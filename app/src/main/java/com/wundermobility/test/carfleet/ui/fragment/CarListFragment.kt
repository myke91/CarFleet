package com.wundermobility.test.carfleet.ui.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import com.wundermobility.test.carfleet.util.Constants.TAG
import kotlin.math.roundToInt


class CarListFragment : BaseFragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var viewModel: CarListViewModel
    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!
    private val markers = mutableMapOf<Marker?, Car>()
    private var isMarkersRemoved: Boolean = false
    private var locationPermissionGranted = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null
    private val GERMANY = LatLngBounds(
        LatLng(47.644173, 7.552175),
        LatLng(54.533735, 13.529632)
    )

    private var width = 0
    private var height = 0
    private var padding: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarListViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocationPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()

        width = resources.displayMetrics.widthPixels
        height = resources.displayMetrics.heightPixels
        padding = (width * 0.12).roundToInt()

        isMarkersRemoved = false

        viewModel.carList.observe(viewLifecycleOwner, { cars ->
            binding.mapView.getMapAsync { map ->
                map.setOnMarkerClickListener(this)
                googleMap = map
                getDeviceLocation()
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(GERMANY, width, height, padding))
                if (cars.isNotEmpty()) {
                    cars.forEach {
                        markers[map.addMarker(
                            MarkerOptions()
                                .visible(true)
                                .position(LatLng(it.lat, it.lon))
                                .title(it.title)
                        )] = it
                    }
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            showDialog(it, null)
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


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach {
                Log.d(TAG, "Permission ${it.key} = ${it.value}")
                val isGranted = it.value
                if (isGranted) {
                    Log.d(TAG, "Permission granted")
                    locationPermissionGranted = true
                    getDeviceLocation()
                } else {
                    Log.d(TAG, "Permission denied")
                }

            }
        }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) && (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            ) {
                showDialog(
                    "Locations permissions needed in order to give you accurate information " +
                            "on where to locate vehicle close to you"
                ) { _, _ ->
                    requestPermissionLauncher.launch(
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        } else {
            locationPermissionGranted = true
            getDeviceLocation()
        }
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        googleMap?.isMyLocationEnabled = true
                        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        googleMap?.isMyLocationEnabled = false
                        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
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
