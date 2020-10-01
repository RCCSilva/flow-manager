package com.rccsilva.flowmanager.domain.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.rccsilva.flowmanager.domain.shared.Message
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SendToNextPublisher(
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun send(message: Message) {
        if (!message.hasNext) {
            return
        }

        val topic = message.nextTopic()

        kafkaTemplate.send(topic, objectMapper.writeValueAsString(message))
    }
}