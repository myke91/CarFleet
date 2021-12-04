package com.wundermobility.test.carfleet.ui.fragment

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.wundermobility.test.carfleet.R

open class BaseFragment: Fragment() {

    protected fun showDialog(message: String, listener: DialogInterface.OnClickListener?){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok, listener)
        builder.show()
    }

}