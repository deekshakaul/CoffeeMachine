package com.dunzo.coffeemachine

import com.dunzo.coffeemachine.managers.CoffeeMachineManager
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class CoffeemachineApplicationTests {

	@Test
	fun contextLoads() {
		File(javaClass.getResource("/multiOutletAllDrinksValid.json").file).inputStream().readBytes().toString(Charsets.UTF_8)?.let {
			print(it)
		}
	}

	@Test
	fun testForSingleOutlet() {
		val inputString = File(javaClass.getResource("/singleOutletUnavailableIngredients.json").file).inputStream().readBytes().toString(Charsets.UTF_8)
		CoffeeMachineManager.setupCoffeeMachine(inputString)
		println("${CoffeeMachineManager.outlets}, ${CoffeeMachineManager.requests}")
		CoffeeMachineManager.processRequests()
	}

}
