package com.rccsilva.flowmanager.domain.shared

import java.util.*

data class HandlerNode(
    val topic: String,
    val handlerId: Int,
    val children: List<HandlerNode>,
    val uuid: String = UUID.randomUUID().toString()
)