package com.dunzo.coffeemachine.task

import com.dunzo.coffeemachine.models.Drink
import com.dunzo.coffeemachine.managers.InventoryManager

public class MakeDrinkTask(drinkToMake: Drink): Runnable {

    var drink: Drink = drinkToMake

    fun makeDrink() {
        InventoryManager.validateIngredientsAndUpdateInventory(drink)
    }

    override fun run() {
        this.makeDrink()
    }
}