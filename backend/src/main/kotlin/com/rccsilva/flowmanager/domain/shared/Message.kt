package com.rccsilva.flowmanager.domain.shared

import com.fasterxml.jackson.annotation.JsonIgnore

data class Message(
    val payload: Payload,
    val topics: List<String>,
    var topicIndex: Int = 0
) {
    val hasNext: Boolean
        @JsonIgnore
        get() = topicIndex < topics.size - 1

    fun currentTopic(): String {
        if (topicIndex >= topics.size) {
            throw IllegalArgumentException("Message does not have another topic")
        }
        return topics[topicIndex]
    }

    fun nextTopic(): String {
        if (!hasNext) {
            throw IllegalArgumentException("Message does not have another topic")
        }

        topicIndex += 1

        return this.currentTopic()
    }
}