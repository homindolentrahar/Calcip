package com.homindolentrahar.calcip.main.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.homindolentrahar.calcip.R
import com.homindolentrahar.calcip.util.Constants
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Calculate
        btn_calculate.setOnClickListener {
            calculate()
        }
//        Find CIDR
        btn_cidr.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_netmaskFragment)
        }
    }

    private fun calculate() {
        if (edit_ip.text.toString().isNotEmpty() && edit_cidr.text.toString().isNotEmpty()) {
            val splittedIpAddress =
                edit_ip.text.toString()
                    .split(".")
                    .filter { it != "" || it.isNotEmpty() }
                    .map { it.toInt() }
            val cidr = edit_cidr.text.toString().toInt()

            if (Constants.checkTotalAddress(splittedIpAddress) && Constants.checkValidCIDR(cidr)){
                if (!Constants.checkValueOfIP(splittedIpAddress)){
                    edit_ip.error = getString(R.string.error_ip_invalid)
                    return
                }
//                Execute Navigate to Result Fragment
                val argument = Bundle()
                argument.putString(ResultFragment.EXTRA_IP,edit_ip.text.toString())
                argument.putInt(ResultFragment.EXTRA_CIDR,edit_cidr.text.toString().toInt())

                findNavController().navigate(R.id.action_mainFragment_to_resultFragment,argument)

            }else{
                if (!Constants.checkTotalAddress(splittedIpAddress)){
                    edit_ip.error = getString(R.string.error_ip_invalid)
                }
                if (!Constants.checkValidCIDR(cidr)){
                    edit_cidr.error = getString(R.string.error_cidr_invalid)
                }
            }
        } else {
            if (edit_ip.text.toString().isEmpty()) {
                edit_ip.error = getString(R.string.error_ip_empty)
            }
            if (edit_cidr.text.toString().isEmpty()) {
                edit_cidr.error = getString(R.string.error_cidr_empty)
            }
        }
    }
}
