package com.wundermobility.test.carfleet.ui.fragment

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.wundermobility.test.carfleet.R

open class BaseFragment: Fragment() {

    protected fun showDialog(message: Int){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(message)
        builder.setMessage(R.string.quick_rent_success)
        builder.setPositiveButton(R.string.ok, null)
        builder.show()
    }

    protected fun showDialog(message: String){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(message)
        builder.setMessage(R.string.quick_rent_success)
        builder.setPositiveButton(R.string.ok, null)
        builder.show()
    }

}