package com.dunzo.coffeemachine

import com.dunzo.coffeemachine.managers.CoffeeMachineManager
import com.dunzo.coffeemachine.managers.InventoryManager
import com.dunzo.coffeemachine.models.Ingredient
import org.junit.After
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class CoffeemachineApplicationTests {

	@AfterEach
	fun reset() {
		Thread.sleep(1000)
		CoffeeMachineManager.stopCoffeeMachine()
		InventoryManager.clearInventory()
	}

	@Test
	@Synchronized
	fun testForSingleOutlet() {
		val inputString = File(javaClass.getResource("/singleOutletUnavailableIngredients.json").file).inputStream().readBytes().toString(Charsets.UTF_8)
		Assert.assertTrue(InventoryManager.inventory.isNullOrEmpty())
		CoffeeMachineManager.setupCoffeeMachine(inputString)
		Assert.assertTrue(!CoffeeMachineManager.outlets.equals(0))
		Assert.assertTrue(CoffeeMachineManager.requests.isNotEmpty())
		Assert.assertTrue(!InventoryManager.inventory.isNullOrEmpty())
		Assert.assertTrue(!CoffeeMachineManager.executor.isShutdown)
		CoffeeMachineManager.processRequests()
	}

	@Test
	@Synchronized
	fun testForMultipleOutletsSufficientIngredients() {
		val inputString = File(javaClass.getResource("/multiOutletAllDrinksValid.json").file).inputStream().readBytes().toString(Charsets.UTF_8)
		Assert.assertTrue(InventoryManager.inventory.isNullOrEmpty())
		CoffeeMachineManager.setupCoffeeMachine(inputString)
		Assert.assertTrue(!InventoryManager.inventory.isNullOrEmpty())
		Assert.assertTrue(!CoffeeMachineManager.executor.isShutdown)
		CoffeeMachineManager.processRequests()
	}

	@Test
	@Synchronized
	fun testForMultipleOutletsInsufficientIngredients() {
		val inputString = File(javaClass.getResource("/multiOutletInsufficientIngredients.json").file).inputStream().readBytes().toString(Charsets.UTF_8)
		Assert.assertTrue(InventoryManager.inventory.isNullOrEmpty())
		CoffeeMachineManager.setupCoffeeMachine(inputString)
		Assert.assertTrue(!InventoryManager.inventory.isNullOrEmpty())
		Assert.assertTrue(!CoffeeMachineManager.executor.isShutdown)
		CoffeeMachineManager.processRequests()
	}

	@Test
	fun testRefillHotWater() {
		val inputString = File(javaClass.getResource("/multiOutletInsufficientIngredients.json").file).inputStream().readBytes().toString(Charsets.UTF_8)
		Assert.assertTrue(InventoryManager.inventory.isNullOrEmpty())
		CoffeeMachineManager.setupCoffeeMachine(inputString)
		InventoryManager.refillIngredient(Ingredient("hot_water", 300))
		Assert.assertEquals(InventoryManager.inventory["hot_water"], 800)
		Assert.assertTrue(!CoffeeMachineManager.executor.isShutdown)
	}
}
