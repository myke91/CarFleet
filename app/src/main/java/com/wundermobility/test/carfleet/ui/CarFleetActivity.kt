package com.wundermobility.test.carfleet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wundermobility.test.carfleet.R
import com.wundermobility.test.carfleet.ui.fragment.CarListFragment

class CarFleetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_fleet_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CarListFragment.newInstance())
                    .commitNow()
        }
    }
}