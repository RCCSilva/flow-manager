package com.rccsilva.flowmanager.domain.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rccsilva.flowmanager.domain.handlers.interfaces.IHandler
import com.rccsilva.flowmanager.domain.shared.Message
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class FilterUsersHandler(
    private val sendToNextPublisher: SendToNextPublisher,
    private val objectMapper: ObjectMapper
): IHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["flow-manager.filter"])
    override fun handleMessage(payload: String) {
        logger.info("Received message to filter: $payload")
        try {
            val message = objectMapper.readValue<Message>(payload)
            sendToNextPublisher.send(message)
        } catch (e: Exception) {
            logger.error("Failed to process message to filter", e)
        }
    }
}