package com.cslab.blovi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BloviApplication

fun main(args: Array<String>) {
	runApplication<BloviApplication>(*args)
}
