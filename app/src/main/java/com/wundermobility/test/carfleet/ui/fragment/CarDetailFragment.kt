package com.wundermobility.test.carfleet.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.wundermobility.test.carfleet.R
import com.wundermobility.test.carfleet.databinding.FragmentCarDetailBinding
import com.wundermobility.test.carfleet.ui.viewmodel.CarDetailViewModel


class CarDetailFragment : BaseFragment() {

    private lateinit var viewModel: CarDetailViewModel
    private var _binding: FragmentCarDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarDetailViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments

        if (bundle != null) {
            val carId = bundle["carId"]
            viewModel.getCarDetail(carId.toString());
        }

        viewModel.car.observe(viewLifecycleOwner, Observer { car ->
            Picasso.get().load(car.vehicleTypeImageUrl)
                .into(binding.carImage)
        })

        viewModel.rentalStatus.observe(viewLifecycleOwner, Observer {
            showRentalStatus()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            showDialog(it, null)
        })
    }

    private fun showRentalStatus(){
        showDialog(getString(R.string.quick_rent_success), null)
    }

    companion object {
        fun newInstance() = CarDetailFragment()
    }

}