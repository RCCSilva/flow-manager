package com.rccsilva.flowmanager.domain.flowmanager.entities

import com.rccsilva.flowmanager.domain.shared.Payload
import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "flows")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType::class
)
data class Flow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "name")
    val name: String,

    @Type(type = "list-array")
    @Column(name = "topics", columnDefinition = "varchar[128][]")
    val topics: List<String>,

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    val payload: Payload
)