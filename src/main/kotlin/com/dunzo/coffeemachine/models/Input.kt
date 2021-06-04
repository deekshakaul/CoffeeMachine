package com.dunzo.coffeemachine.models

import com.fasterxml.jackson.annotation.JsonInclude

data class Input (
        val machine: Machine
)

data class Machine (
        val outlets : Outlets,
        val total_items_quantity : HashMap<String, Int>,
        val beverages: HashMap<String, HashMap<String, Int>>
)

data class Outlets (
        val count_n: Int
)

