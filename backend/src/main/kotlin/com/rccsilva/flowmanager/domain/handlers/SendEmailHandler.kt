package com.rccsilva.flowmanager.domain.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rccsilva.flowmanager.domain.flowmanager.entities.FlowLog
import com.rccsilva.flowmanager.domain.flowmanager.repositories.FlowLogRepository
import com.rccsilva.flowmanager.domain.handlers.interfaces.IHandler
import com.rccsilva.flowmanager.domain.handlers.publishers.SendToNextPublisher
import com.rccsilva.flowmanager.domain.shared.Message
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class SendEmailHandler(
    private val sendToNextPublisher: SendToNextPublisher,
    private val flowLogRepository: FlowLogRepository,
    private val objectMapper: ObjectMapper
): IHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["flow-manager.send-email"])
    override fun handleMessage(payload: String) {
        logger.info("Received message to send-email: $payload")
        try {
            val message = objectMapper.readValue<Message>(payload)
            val newPayload = processMessage(message)
            sendToNextPublisher.send(newPayload, message)
        } catch (e: Exception) {
            logger.error("Failed to process message to send-email", e)
        }
    }

    private fun processMessage(message: Message) = message.payload
}