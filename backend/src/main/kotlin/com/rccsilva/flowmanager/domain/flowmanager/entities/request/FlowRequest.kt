package com.rccsilva.flowmanager.domain.flowmanager.entities.request

data class FlowRequest (
    val name: String,
    val handlers: HandlerRequest
)

data class HandlerRequest (
    val id: Int,
    val children: List<HandlerRequest>
)

