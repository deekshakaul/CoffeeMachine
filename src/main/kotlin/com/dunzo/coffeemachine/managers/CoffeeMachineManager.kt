package com.dunzo.coffeemachine.managers

import com.dunzo.coffeemachine.models.Drink
import com.dunzo.coffeemachine.models.Ingredient
import com.dunzo.coffeemachine.models.Input
import com.dunzo.coffeemachine.task.MakeDrinkTask
import com.google.gson.Gson
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext


public class CoffeeMachineManager {

    companion object {

        var outlets: Int = 0
        lateinit var requests: HashMap<String, HashMap<String, Int>>
        lateinit var executor: ExecutorService

        fun setupCoffeeMachine(inputJsonAsString: String) {
            val input = Gson().fromJson(inputJsonAsString, Input::class.java)
            InventoryManager.instance.makeOrUpdateInventory(input.machine.total_items_quantity)
            outlets = input.machine.outlets.count_n
            requests = input.machine.beverages
            executor = Executors.newFixedThreadPool(outlets)
        }

        fun processRequests() {
            var ingredientList: CopyOnWriteArrayList<Ingredient> = CopyOnWriteArrayList()
            requests.forEach { (name, ingredients) ->
                ingredients.forEach { ingredient ->
                    ingredientList.add(Ingredient(ingredient.key, ingredient.value))
                }
                placeOrder(Drink(name, ingredientList))
                ingredientList.clear()
            }
        }

        fun placeOrder(drink: Drink) {
            val makeDrinkTask = MakeDrinkTask(drink)
            executor.execute(makeDrinkTask)
        }
    }


}