package com.homindolentrahar.calcip.util

import kotlin.math.pow

object Constants {
    val listBlock = listOf(0, 128, 192, 224, 240, 248, 252, 254, 255)
    val listNetmask = listOf(
        "128",
        "192",
        "224",
        "240",
        "248",
        "252",
        "254",
        "255",
        "255.0.0.0",
        "255.128.0.0",
        "255.192.0.0",
        "255.224.0.0",
        "255.240.0.0",
        "255.248.0.0",
        "255.252.0.0",
        "255.254.0.0",
        "255.255.0.0",
        "255.255.128.0",
        "255.255.192.0",
        "255.255.224.0",
        "255.255.240.0",
        "255.255.248.0",
        "255.255.252.0",
        "255.255.254.0",
        "255.255.255.0",
        "255.255.255.128",
        "255.255.255.192",
        "255.255.255.224",
        "255.255.255.240",
        "255.255.255.248",
        "255.255.255.252",
        "255.255.255.254",
        "255.255.255.255"
    )

    fun getNetworkAddress(ipAddress: List<Int>, netmaskAddress: List<Int>): String {
        val listNetwork = mutableListOf<Int>()

        for (octate in 0..3) {
            val network = ipAddress[octate] and netmaskAddress[octate]
            listNetwork.add(network)
        }

        return listNetwork.joinToString(".")
    }

    fun convertAddressToBinary(listAddress: List<Int>): String {
        val list = mutableListOf<String>()

        for (address in listAddress) {
            val binary = Integer.toBinaryString(address)
            list.add(binary)
        }

        return list.joinToString(".")
    }

    fun getCidr(binaryAddress: String): Int {
        return binaryAddress.count { it == '1' }
    }

    fun getTotalSubnet(cidr: Int): Int {
        return 2.0.pow(cidr % 8).toInt()
    }

    fun getTotalHost(cidr: Int): Int {
        return 2.0.pow(32 - cidr).toInt()
    }

    fun checkTotalAddress(address: List<Int>): Boolean {
        return address.size == 4
    }

    fun checkValidCIDR(cidr: Int): Boolean {
        return cidr in 8..30
    }

    fun checkValueOfIP(ipAddress: List<Int>): Boolean {
        val firstOctate = ipAddress[0] in 1..255
        val secondOctate = ipAddress[1] in 0..255
        val thirdOctate = ipAddress[2] in 0..255
        val fourthOctate = ipAddress[3] in 0..255

        return firstOctate && secondOctate && thirdOctate && fourthOctate
    }

    fun checkValueOfNetmask(netmaskAddress: List<Int>): Boolean {
        val firstOctate = netmaskAddress[0] == 255 && netmaskAddress[0] > 0
        val secondOctate = netmaskAddress[1] in listBlock
        val thirdOctate = netmaskAddress[2] in listBlock
        val fourthOctate = netmaskAddress[3] in listBlock

        return firstOctate && secondOctate && thirdOctate && fourthOctate
    }

    fun checkZeroValueOfNetmask(netmaskAddress: List<Int>): Boolean {
        var thirdOctate = true
        var fourthOctate = true

        if (netmaskAddress[1] == 0 || netmaskAddress[0] != 255) {
            thirdOctate = netmaskAddress[2] in 0..0
            fourthOctate = netmaskAddress[3] in 0..0
        }
        if (netmaskAddress[2] == 0 || netmaskAddress[2] != 255) {
            fourthOctate = netmaskAddress[3] in 0..0
        }

        return thirdOctate && fourthOctate
    }

}