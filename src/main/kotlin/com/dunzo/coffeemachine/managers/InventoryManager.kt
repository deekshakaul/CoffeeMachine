package com.dunzo.coffeemachine.managers

import com.dunzo.coffeemachine.constants.CoffeeMachineConstants
import com.dunzo.coffeemachine.models.Drink
import com.dunzo.coffeemachine.models.Ingredient
import org.slf4j.LoggerFactory
import java.lang.String.format


// Since there will only be one single inventory, makes sense to keep this as a singleton class
object InventoryManager {

    private val log = LoggerFactory.getLogger(InventoryManager::class.java)

    var inventory: MutableMap<String, Int> = mutableMapOf()

    fun makeOrUpdateInventory(ingredients: MutableMap<String, Int>) {
        ingredients.forEach { item ->
            val currentQuantity = inventory[item.key] ?: 0
            inventory[item.key] = currentQuantity + item.value
        }
    }

    fun isIngredientEnough(ingredient: Ingredient): Boolean {
        inventory[ingredient.name]?.let { quantity ->
            return quantity >= ingredient.quantity
        }
        return false
    }

    fun isIngredientAvailable(ingredient: Ingredient): Boolean {
        return inventory[ingredient.name] != null
    }

    // Update the inventory everytime an ingredient is used to make a drink
    @Synchronized
    fun useIngredient(ingredient: Ingredient) {
        inventory[ingredient.name]?.let {
            inventory[ingredient.name] = inventory[ingredient.name]?.minus(ingredient.quantity) ?: 0
            if (it < CoffeeMachineConstants.thresholdQuantity) {
                notifyLowQuantity(ingredient.name)
            }
        }
    }

    @Synchronized
    fun validateIngredientsAndUpdateInventory(drink: Drink) {
        var canMake = true
        var unavailable = ""
        var insufficient = ""
        drink.ingredients.forEach { item ->
            if (!isIngredientAvailable((item))) {
                canMake = false
                unavailable = item.name
            } else if (!isIngredientEnough(item)) {
                canMake = false
                insufficient = item.name
            }
        }
        if (canMake) {
            drink.ingredients.forEach { item ->
               useIngredient(item)
            }
            println("${format(CoffeeMachineConstants.drinkMade, drink.name)}")
        } else {

            // Even if multiple ingredients are unavailable or insufficient, returning only one missing ingredient for now
            // as irrespective of the details the drink can still not be made. If the coffee machine user has to be notified
            // what all ingredients are missing then we need to identify what will be the message format. It can be easily implemented
            // by making unavailable and insufficient into arrays and formatting the message accordingly.

            if (unavailable.isNotBlank()) {
                println("${format(CoffeeMachineConstants.unavailableIngredient, drink.name, unavailable)}")
            } else {
                println("${format(CoffeeMachineConstants.insufficientIngredient, drink.name, insufficient)}")
            }
        }
    }

    fun notifyLowQuantity(ingredientName: String) {
        println(format(CoffeeMachineConstants.runningLow, ingredientName))
    }

    fun refillIngredient(ingredient: Ingredient) {
        inventory[ingredient.name] = inventory[ingredient.name]?.plus(ingredient.quantity) ?: ingredient.quantity
    }

    fun clearInventory() {
        inventory.clear()
        println("${inventory}")
    }
}