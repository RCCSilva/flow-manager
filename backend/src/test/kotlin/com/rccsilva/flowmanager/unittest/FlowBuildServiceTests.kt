package com.rccsilva.flowmanager.unittest

import com.rccsilva.flowmanager.domain.flowmanager.entities.Handler
import com.rccsilva.flowmanager.domain.flowmanager.entities.request.FlowRequest
import com.rccsilva.flowmanager.domain.flowmanager.entities.request.HandlerRequest
import com.rccsilva.flowmanager.domain.flowmanager.repositories.HandlerRepository
import com.rccsilva.flowmanager.domain.flowmanager.services.FlowBuildService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FlowBuildServiceTests {
    private lateinit var flowBuildService: FlowBuildService
    private lateinit var handlerRepository: HandlerRepository

    @BeforeEach
    fun setUp() {
        handlerRepository = mockk()
        flowBuildService = FlowBuildService(handlerRepository)
    }

    @Test
    fun `Test create, given a FlowRequest with a "list" of HandlerRequest, validate response`() {
        // Given
        val flowRequest = FlowRequest(
            "Hello",
            HandlerRequest(
                1,
                children = listOf(
                    HandlerRequest(
                        2,
                        emptyList()
                    )
                )
            )
        )

        every {
            handlerRepository.findAll()
        } returns listOf(
            Handler(1, "First handler", "topic1"),
            Handler(2, "Second handler", "topic2")
        ) as Iterable<Handler>

        // Test
        val  flow = flowBuildService.create(flowRequest)

        // Assert
        assertThat(flow.name).isEqualTo("Hello")
        assertThat(flow.topicNode.value).isEqualTo("topic1")
        assertThat(flow.topicNode.children.size).isEqualTo(1)
        assertThat(flow.topicNode.children[0].value).isEqualTo("topic2")
        assertThat(flow.topicNode.children[0].children.size).isEqualTo(0)
    }


    @Test
    fun `Test create, given a FlowRequest with a "complex" of HandlerRequest, validate response`() {
        // Given
        val flowRequest = FlowRequest(
            "Hello",
            HandlerRequest(
                1,
                listOf(
                    HandlerRequest(
                        2,
                        listOf(
                            HandlerRequest(
                                5,
                                emptyList()
                            )
                        )
                    ),
                    HandlerRequest(
                        3,
                        emptyList()
                    ),
                    HandlerRequest(
                        4,
                        listOf(
                            HandlerRequest(
                                6,
                                emptyList()
                            )
                        )
                    )
                )
            )
        )

        every {
            handlerRepository.findAll()
        } returns listOf(
            Handler(1, "First handler", "topic1"),
            Handler(2, "Second handler", "topic2"),
            Handler(3, "Third handler", "topic3"),
            Handler(4, "Fourth handler", "topic4"),
            Handler(5, "Fifth handler", "topic5"),
            Handler(6, "Sixth handler", "topic6")
        ) as Iterable<Handler>

        // Test
        val  flow = flowBuildService.create(flowRequest)

        // Assert
        assertThat(flow.name).isEqualTo("Hello")
        // Depth 1
        assertThat(flow.topicNode.value).isEqualTo("topic1")
        assertThat(flow.topicNode.children.size).isEqualTo(3)

        // Depth 2
        assertThat(flow.topicNode.children[0].value).isEqualTo("topic2")
        assertThat(flow.topicNode.children[0].children.size).isEqualTo(1)
        assertThat(flow.topicNode.children[1].value).isEqualTo("topic3")
        assertThat(flow.topicNode.children[1].children.size).isEqualTo(0)
        assertThat(flow.topicNode.children[2].value).isEqualTo("topic4")
        assertThat(flow.topicNode.children[2].children.size).isEqualTo(1)

        // Depth 3
        assertThat(flow.topicNode.children[0].children[0].value).isEqualTo("topic5")
        assertThat(flow.topicNode.children[0].children[0].children.size).isEqualTo(0)

        assertThat(flow.topicNode.children[2].children[0].value).isEqualTo("topic6")
        assertThat(flow.topicNode.children[2].children[0].children.size).isEqualTo(0)
    }
}