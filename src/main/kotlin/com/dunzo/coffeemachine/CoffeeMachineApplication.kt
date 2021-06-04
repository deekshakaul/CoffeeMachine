package com.dunzo.coffeemachine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import java.io.File

@SpringBootApplication
@ComponentScan
class CoffeeMachineApplication

fun main(args: Array<String>) {
	val runApplication = runApplication<CoffeeMachineApplication>(*args)
}
