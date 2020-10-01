package com.rccsilva.flowmanager.domain.flowmanager.entities

import com.rccsilva.flowmanager.domain.flowmanager.enums.FlowLoggingStatus
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "flow_logs")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class FlowLogs(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "flow_id")
    val flow: Flow,

    @ManyToOne
    @JoinColumn(name = "handler_id")
    val handler: Handler,

    @Enumerated(EnumType.STRING)
    val status: FlowLoggingStatus,

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    val detail: Map<String, Any>?
)