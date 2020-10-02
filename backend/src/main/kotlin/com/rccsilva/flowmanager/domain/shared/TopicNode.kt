package com.rccsilva.flowmanager.domain.shared

import com.rccsilva.flowmanager.domain.shared.interfaces.INode

data class TopicNode (
    override val value: String,
    override val next: Set<TopicNode>
) : INode<String>