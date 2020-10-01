package com.rccsilva.flowmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlowManagerApplication

fun main(args: Array<String>) {
	runApplication<FlowManagerApplication>(*args)
}
