package com.homindolentrahar.calcip.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.homindolentrahar.calcip.R
import com.homindolentrahar.calcip.util.Constants
import kotlinx.android.synthetic.main.fragment_netmask.*

/**
 * A simple [Fragment] subclass.
 */
class NetmaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_netmask, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Calculate CIDR
        btn_calculate.setOnClickListener {
            calculateCIDR()
        }
//        Go back
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun calculateCIDR() {
        if (edit_netmask.text.toString().isNotEmpty()){
            val splittedNetmaskAddress = edit_netmask.text.toString().split(".").filter { it != "" || it.isNotEmpty() }.map { it.toInt() }

            if (Constants.checkTotalAddress(splittedNetmaskAddress)){
                if (!Constants.checkValueOfNetmask(splittedNetmaskAddress)){
                    edit_netmask.error = getString(R.string.error_netmask_invalid)
                    return
                }
                if (!Constants.checkZeroValueOfNetmask(splittedNetmaskAddress)){
                    edit_netmask.error = getString(R.string.error_netmask_invalid)
                    return
                }
//                Result
                val binary = Constants.convertAddressToBinary(splittedNetmaskAddress)
                val cidr = Constants.getCidr(binary)
                val result = "/$cidr"

                tv_cidr.text = result

            }else{
                edit_netmask.error = getString(R.string.error_netmask_invalid)
            }
        }else{
            edit_netmask.error = getString(R.string.error_netmask_empty)
        }
    }
}
