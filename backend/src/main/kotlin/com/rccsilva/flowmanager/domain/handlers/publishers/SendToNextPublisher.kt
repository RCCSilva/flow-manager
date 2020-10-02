package com.rccsilva.flowmanager.domain.handlers.publishers

import com.fasterxml.jackson.databind.ObjectMapper
import com.rccsilva.flowmanager.domain.shared.Message
import com.rccsilva.flowmanager.domain.shared.Payload
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SendToNextPublisher(
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun send(payload: Payload, message: Message) {
        if (!message.hasNext) {
            return
        }

        message.buildNextMessages(payload)?.forEach { newMessage ->
            newMessage.currentTopic?.let { topic ->
                logger.info("Sending $topic to $newMessage")
                kafkaTemplate.send(topic, objectMapper.writeValueAsString(newMessage))
            }
        }
    }
}