package com.dunzo.coffeemachine.constants

class CoffeeMachineConstants {
    companion object {
        const val drinkMade = "%s is prepared"
        const val unavailableIngredient = "%s cannot be prepared because %s is not available"
        const val insufficientIngredient = "%s cannot be prepared because %s is not sufficient"
        const val runningLow = "%s is running low"
        const val thresholdQuantity = 500
    }
}