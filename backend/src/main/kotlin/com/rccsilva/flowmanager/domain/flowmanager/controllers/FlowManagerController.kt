package com.rccsilva.flowmanager.domain.flowmanager.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.rccsilva.flowmanager.domain.flowmanager.entities.Flow
import com.rccsilva.flowmanager.domain.flowmanager.entities.request.FlowRequest
import com.rccsilva.flowmanager.domain.flowmanager.repositories.FlowRepository
import com.rccsilva.flowmanager.domain.flowmanager.repositories.HandlerRepository
import com.rccsilva.flowmanager.domain.flowmanager.services.FlowBuildService
import com.rccsilva.flowmanager.domain.shared.Message
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/flow-manager")
class FlowManagerController(
    private val flowRepository: FlowRepository,
    private val handlerRepository: HandlerRepository,
    private val flowBuildService: FlowBuildService,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    @GetMapping("handlers")
    fun getHandlers() = ResponseEntity.ok(handlerRepository.findAll().toList())

    @GetMapping("flow/{flowId}")
    fun getFlow(@PathVariable flowId: Int): ResponseEntity<Flow> {
        val flow = flowRepository.findByIdOrNull(flowId)
            ?: throw IllegalArgumentException("Flow with $flowId id not found")
        return ResponseEntity.ok(flow)
    }

    @PostMapping("flow")
    fun create(@RequestBody flowRequest: FlowRequest): ResponseEntity<Flow> {

        val flow = flowBuildService.create(flowRequest)

        val message = Message(
            payload = flow.payload,
            handlerNode = flow.handlerNode
        )

        message.currentTopic?.let { topic ->
            kafkaTemplate.send(
                topic,
                objectMapper.writeValueAsString(message)
            )
        }

        return ResponseEntity.ok(flowRepository.save(flow))
    }
}