package com.homindolentrahar.calcip.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class IPResponse(
    val listIpResult: List<IPResult>
)

@Parcelize
data class IPResult(
    val ipAddress: String,
    val netmaskAddress: String,
    val networkAddress: String,
    val firstHostAddress: String,
    val lastHostAddress: String,
    val broadcastAddress: String,
    val cidr: Int,
    val subnetBlock: Int,
    val totalSubnet: Int,
    val totalValidHost: Int
) : Parcelable