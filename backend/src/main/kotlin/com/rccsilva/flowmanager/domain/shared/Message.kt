package com.rccsilva.flowmanager.domain.shared

import com.fasterxml.jackson.annotation.JsonIgnore

data class Message(
    val
    val payload: Payload?,
    val handlerNode: HandlerNode?
) {
    val hasNext: Boolean
        @JsonIgnore
        get() = handlerNode?.children?.isNotEmpty() ?: false

    val currentTopic: String?
        @JsonIgnore
        get() = handlerNode?.topic

    fun buildNextMessages(payload: Payload?): List<Message>? {
        return handlerNode?.children?.map { node ->
            Message(payload, node)
        }
    }
}