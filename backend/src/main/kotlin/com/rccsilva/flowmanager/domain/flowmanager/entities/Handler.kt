package com.rccsilva.flowmanager.domain.flowmanager.entities

import javax.persistence.*

@Entity
@Table(name = "handlers")
data class Handler (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "topic")
    val topic: String
)