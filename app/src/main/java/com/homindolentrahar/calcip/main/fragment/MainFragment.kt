package com.homindolentrahar.calcip.main.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.homindolentrahar.calcip.R
import com.homindolentrahar.calcip.util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Button calculate clicked
        btn_calculate.setOnClickListener {
            //        Perform Checking Address
            if (edit_ip.text.toString().isNotEmpty() && edit_netmask.text.toString().isNotEmpty()) {

                val splittedIpAddress =
                    edit_ip.text.toString().split(".")
                        .filter { it != "" || it.isNotEmpty() }
                        .map { it.toInt() }
                val splittedNetmaskAddress =
                    edit_netmask.text.toString().split(".")
                        .filter { it != "" || it.isNotEmpty() }
                        .map { it.toInt() }

                if (checkTotalOctateIP(splittedIpAddress) && checkTotalOctateNetmask(
                        splittedNetmaskAddress
                    )
                ) {
//                Another checking
                    if (!checkValueOfIP(splittedIpAddress)) {
                        edit_ip.error = getString(R.string.error_ip_invalid)
                        return@setOnClickListener
                    }
                    if (!checkValueOfNetmask(splittedNetmaskAddress)) {
                        edit_netmask.error = getString(R.string.error_netmask_invalid)
                        return@setOnClickListener
                    }
                    if (!checkNetmaskZeroValue(splittedNetmaskAddress)) {
                        edit_netmask.error = getString(R.string.error_netmask_invalid)
                        return@setOnClickListener
                    }

//                Execution block
                    val argument = Bundle()
                    argument.putString(ResultFragment.EXTRA_IP, edit_ip.text.toString())
                    argument.putString(ResultFragment.EXTRA_NETMASK, edit_netmask.text.toString())
                    findNavController().navigate(
                        R.id.action_mainFragment_to_resultFragment,
                        argument
                    )

                } else {
                    if (!checkTotalOctateIP(splittedIpAddress)) {
                        edit_ip.error = getString(R.string.error_ip_invalid)
                    }
                    if (!checkTotalOctateNetmask(splittedNetmaskAddress)) {
                        edit_netmask.error = getString(R.string.error_netmask_invalid)
                    }
                }
            } else {
                if (edit_ip.text.toString().isEmpty()) {
                    edit_ip.error = getString(R.string.error_ip_empty)
                }
                if (edit_netmask.text.toString().isEmpty()) {
                    edit_netmask.error = getString(R.string.error_netmask_empty)
                }
            }
        }
    }

    private fun checkTotalOctateIP(splittedIp: List<Int>): Boolean {
        return splittedIp.size == 4
    }

    private fun checkTotalOctateNetmask(splittedNetmask: List<Int>): Boolean {
        return splittedNetmask.size == 4
    }

    private fun checkValueOfIP(splittedIp: List<Int>): Boolean {
        val firstOctate = splittedIp[0] in 1..255
        val secondOctate = splittedIp[1] in 1..255
        val thirdOctate = splittedIp[2] in 1..255
        val fourthOctate = splittedIp[3] in 1..255

        return firstOctate && secondOctate && thirdOctate && fourthOctate
    }

    private fun checkValueOfNetmask(splittedNetmask: List<Int>): Boolean {
        val firstOctate = splittedNetmask[0] == 255 && splittedNetmask[0] > 0
        val secondOctate = splittedNetmask[1] in Constants.listBlock
        val thirdOctate = splittedNetmask[2] in Constants.listBlock
        val fourthOctate = splittedNetmask[3] in Constants.listBlock

        return firstOctate && secondOctate && thirdOctate && fourthOctate
    }

    private fun checkNetmaskZeroValue(splittedNetmask: List<Int>): Boolean {
        var thirdOctate = true
        var fourthOctate = true

        if (splittedNetmask[1] == 0 || splittedNetmask[1] != 255) {
            thirdOctate = splittedNetmask[2] in 0..0
            fourthOctate = splittedNetmask[3] in 0..0
        } else if (splittedNetmask[2] == 0 || splittedNetmask[2] != 255) {
            fourthOctate = splittedNetmask[3] in 0..0
        }

        return thirdOctate && fourthOctate
    }

}
