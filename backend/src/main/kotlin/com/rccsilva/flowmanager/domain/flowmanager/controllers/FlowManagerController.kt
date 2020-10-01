package com.rccsilva.flowmanager.domain.flowmanager.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rccsilva.flowmanager.domain.flowmanager.entities.Flow
import com.rccsilva.flowmanager.domain.flowmanager.entity.FlowRepository
import com.rccsilva.flowmanager.domain.shared.Message
import com.rccsilva.flowmanager.domain.shared.Payload
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/v1/flow-manager")
class FlowManagerController(
    private val flowRepository: FlowRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    @GetMapping("{flowId}")
    fun getFlow(@PathVariable flowId: Int): ResponseEntity<Flow> {
        val flow = flowRepository.findByIdOrNull(flowId)
            ?: throw IllegalArgumentException("Flow with $flowId id not found")
        return ResponseEntity.ok(flow)
    }


    @PostMapping
    fun create(@RequestBody flow: Flow): ResponseEntity<Flow> {

        val message = Message(
            payload = flow.payload,
            topics = flow.topics
        )

        kafkaTemplate.send(
            message.currentTopic(),
            objectMapper.writeValueAsString(message)
        )

        return ResponseEntity.ok(flowRepository.save(flow))
    }
}