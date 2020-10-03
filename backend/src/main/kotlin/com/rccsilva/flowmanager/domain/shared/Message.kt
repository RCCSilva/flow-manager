package com.rccsilva.flowmanager.domain.shared

import com.fasterxml.jackson.annotation.JsonIgnore

data class Message(
    val payload: Payload?,
    val topicNode: TopicNode?
) {
    val hasNext: Boolean
        @JsonIgnore
        get() = topicNode?.children?.isNotEmpty() ?: false

    val currentTopic: String?
        @JsonIgnore
        get() = topicNode?.value

    fun buildNextMessages(payload: Payload?): List<Message>? {
        return topicNode?.children?.map { node ->
            Message(payload, node)
        }
    }
}