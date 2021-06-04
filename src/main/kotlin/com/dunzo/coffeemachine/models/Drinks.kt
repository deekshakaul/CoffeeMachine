package com.dunzo.coffeemachine.models

import java.util.concurrent.CopyOnWriteArrayList

public class Ingredient(var name: String, var quantity: Int = 0)

public class Drink(name: String, ingredients: CopyOnWriteArrayList<Ingredient>) {


    var name: String = name
    var ingredients: CopyOnWriteArrayList<Ingredient> = ingredients
}