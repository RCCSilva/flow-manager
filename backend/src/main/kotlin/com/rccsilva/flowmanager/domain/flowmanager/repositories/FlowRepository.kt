package com.rccsilva.flowmanager.domain.flowmanager.repositories

import com.rccsilva.flowmanager.domain.flowmanager.entities.Flow
import org.springframework.data.repository.CrudRepository

interface FlowRepository: CrudRepository<Flow, Int>