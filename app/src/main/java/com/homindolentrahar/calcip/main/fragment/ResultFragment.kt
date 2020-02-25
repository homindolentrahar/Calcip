package com.homindolentrahar.calcip.main.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.calcip.R
import com.homindolentrahar.calcip.adapter.IPAdapter
import com.homindolentrahar.calcip.model.IPResult
import com.homindolentrahar.calcip.util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_result.*
import kotlin.math.pow

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : DaggerFragment() {

    companion object {
        const val EXTRA_IP = "EXTRA_IP"
        const val EXTRA_NETMASK = "EXTRA_NETMASK"
    }

    private lateinit var adapter: IPAdapter
    private val listIpData = mutableListOf<IPResult>()
    private var networkAddress = "null"
    private var firstHostAddress = "null"
    private var lastHostAddress = "null"
    private var broadcastAddress = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Get data from MainFragment
        val ipAddress = arguments?.getString(EXTRA_IP) as String
        val netmaskAddress = arguments?.getString(EXTRA_NETMASK) as String
//        RecyclerView setup
        adapter = IPAdapter {
            Snackbar.make(rv_list_ip, it.networkAddress, Snackbar.LENGTH_SHORT).show()
        }
        rv_list_ip.adapter = adapter
//        Calculate
        calculate(ipAddress, netmaskAddress)
//        Try Again Button
        btn_try_again.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_mainFragment)
        }
    }

    private fun calculate(ipAddress: String, netmaskAddress: String) {
        val splittedIpAddress = ipAddress.split(".").map { it.toInt() }
        val splittedNetmaskAddress = netmaskAddress.split(".").map { it.toInt() }
        networkAddress = getNetworkAddress(splittedIpAddress, splittedNetmaskAddress)
        val splittedNetworkAddress = networkAddress.split(".").map { it.toInt() }
        val cidr = getCidr(convertAddressToBinary(splittedNetmaskAddress))
        val totalSubnet = 2.0.pow(cidr % 8).toInt()
        val totalHost = 2.0.pow(32 - cidr).toInt()
        val subnetBlock = 256 - Constants.listBlock[cidr % 8]
        val hostBlock = cidr.div(8)

        defineHostBlock(hostBlock, subnetBlock, splittedNetworkAddress)

        populateView(
            ipAddress,
            netmaskAddress,
            networkAddress,
            firstHostAddress,
            lastHostAddress,
            broadcastAddress,
            cidr,
            totalSubnet,
            totalHost,
            subnetBlock,
            hostBlock,
            splittedNetworkAddress
        )

    }

    @SuppressLint("SetTextI18n")
    private fun populateView(
        ipAddress: String,
        netmaskAddress: String,
        networkAddress: String,
        firstHost: String,
        lastHost: String,
        broadcast: String,
        cidr: Int,
        totalSubnet: Int,
        totalHost: Int,
        subnetBlock: Int,
        hostBlock: Int,
        splittedNetworkAddress: List<Int>
    ) {
        tv_ip.text = ipAddress
        tv_netmask.text = netmaskAddress
        tv_network.text = networkAddress
        tv_cidr.text = "/$cidr"
        tv_first_host.text = firstHost
        tv_last_host.text = lastHost
        tv_broadcast.text = broadcast
        tv_total_subnet.text = totalSubnet.toString()
        tv_total_host.text = totalHost.toString()
        tv_block_subnet.text = subnetBlock.toString()

        defineAllData(
            totalSubnet,
            subnetBlock,
            hostBlock,
            splittedNetworkAddress
        )

        adapter.submitList(listIpData)
    }

    private fun defineHostBlock(
        hostBlock: Int,
        subnetBlock: Int,
        splittedNetworkAddress: List<Int>
    ) {
        firstHostAddress =
            splittedNetworkAddress.toMutableList().also { it[3] += 1 }.joinToString(".")
        when (hostBlock) {
            1 -> {
                lastHostAddress = splittedNetworkAddress.toMutableList().also {
                    it[3] = 254
                    it[2] = 255
                    it[1] += (subnetBlock - 1)
                }.joinToString(".")
                broadcastAddress = splittedNetworkAddress.toMutableList().also {
                    it[3] = 255
                    it[2] = 255
                    it[1] += (subnetBlock - 1)
                }.joinToString(".")
            }
            2 -> {
                lastHostAddress = splittedNetworkAddress.toMutableList().also {
                    it[3] = 254
                    it[2] += (subnetBlock - 1)
                }.joinToString(".")
                broadcastAddress = splittedNetworkAddress.toMutableList().also {
                    it[3] = 255
                    it[2] += (subnetBlock - 1)
                }.joinToString(".")
            }
            3 -> {
                lastHostAddress = splittedNetworkAddress.toMutableList().also {
                    it[3] += (subnetBlock - 2)
                }.joinToString(".")
                broadcastAddress = splittedNetworkAddress.toMutableList().also {
                    it[3] += (subnetBlock - 1)
                }.joinToString(".")
            }
        }
    }

    private fun defineAllData(
        totalSubnet: Int,
        subnetBlock: Int,
        hostBlock: Int,
        splittedNetworkAddress: List<Int>
    ) {
        val starterNetwork = splittedNetworkAddress.toMutableList().also {
            for (block in 3 downTo hostBlock) {
                it[block] = 0
            }
        }
        var nextSubnet = 0

        for (subnet in 1..totalSubnet) {
            val network = starterNetwork.toMutableList().also { it[hostBlock] = nextSubnet }
            networkAddress = network.joinToString(".")
            defineHostBlock(hostBlock, subnetBlock, network)

            val cuttedNetworkAddress =
                network.toMutableList().subList(hostBlock, 4).joinToString(".")
            val cuttedFirstHost =
                firstHostAddress.split(".").toMutableList().subList(hostBlock, 4).joinToString(".")
            val cuttedLastHost =
                lastHostAddress.split(".").toMutableList().subList(hostBlock, 4).joinToString(".")
            val cuttedBroadcast =
                broadcastAddress.split(".").toMutableList().subList(hostBlock, 4).joinToString(".")

            val ipData = IPResult(
                id = subnet,
                networkAddress = cuttedNetworkAddress,
                firstHostAddress = cuttedFirstHost,
                lastHostAddress = cuttedLastHost,
                broadcastAddress = cuttedBroadcast
            )

            listIpData.add(ipData)
            nextSubnet += subnetBlock
        }

    }

    private fun getNetworkAddress(ipAddress: List<Int>, netmaskAddress: List<Int>): String {
        val listNetwork = mutableListOf<Int>()

        for (octate in 0..3) {
            val network = ipAddress[octate] and netmaskAddress[octate]
            listNetwork.add(network)
        }

        return listNetwork.joinToString(".")
    }

    private fun convertAddressToBinary(address: List<Int>): String {
        val listBinary = mutableListOf<String>()

        for (octate in 0..3) {
            val binary = Integer.toBinaryString(address[octate])
            listBinary.add(binary)
        }

        return listBinary.joinToString(".")
    }

    private fun getCidr(binaryAddress: String): Int {
        return binaryAddress.count { it == '1' }
    }

}
