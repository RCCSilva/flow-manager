package com.rccsilva.flowmanager.domain.flowmanager.services

import com.rccsilva.flowmanager.domain.flowmanager.entities.Flow
import com.rccsilva.flowmanager.domain.flowmanager.entities.request.FlowRequest
import com.rccsilva.flowmanager.domain.flowmanager.entities.request.HandlerRequest
import com.rccsilva.flowmanager.domain.flowmanager.repositories.HandlerRepository
import com.rccsilva.flowmanager.domain.shared.TopicNode
import org.springframework.stereotype.Service

@Service
class FlowBuildService(
    private val handlerRepository: HandlerRepository
) {
    fun create(flowRequest: FlowRequest): Flow {
        val handlers =
            handlerRepository
                .findAll()
                .associateBy { it.id!! }

        val topicNode = runner(flowRequest.handlers) { id ->
            handlers[id]?.topic
                ?: throw IllegalArgumentException("Handler with $id id does not have a topic mapped")
        }

        return Flow(name = flowRequest.name, topicNode = topicNode)
    }

    private fun runner(
        handlerRequest: HandlerRequest,
        getTopic: (Int?) -> String
    ): TopicNode {
        if (handlerRequest.children.isEmpty()) {
            return TopicNode(getTopic(handlerRequest.id), emptyList())
        }

        val children = handlerRequest.children.map { runner(it, getTopic) }

        return TopicNode(getTopic(handlerRequest.id), children)
    }
}