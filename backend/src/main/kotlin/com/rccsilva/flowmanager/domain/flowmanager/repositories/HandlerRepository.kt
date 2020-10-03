package com.rccsilva.flowmanager.domain.flowmanager.repositories

import com.rccsilva.flowmanager.domain.flowmanager.entities.Handler
import org.springframework.data.repository.CrudRepository

interface HandlerRepository: CrudRepository<Handler, Int>