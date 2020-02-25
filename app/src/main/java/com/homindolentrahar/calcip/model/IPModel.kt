package com.homindolentrahar.calcip.model

data class IPResult(
    val id: Int,
    val networkAddress: String,
    val firstHostAddress: String,
    val lastHostAddress: String,
    val broadcastAddress: String
)