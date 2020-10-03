package com.rccsilva.flowmanager.domain.flowmanager.repositories

import com.rccsilva.flowmanager.domain.flowmanager.entities.FlowLog
import org.springframework.data.repository.CrudRepository

interface FlowLogRepository: CrudRepository<FlowLog, Long>