package com.dunzo.coffeemachine.models

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Input (
        val machine: Machine
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Machine (

        val outlets : Outlets,
        val total_items_quantity : HashMap<String, Int>,
        val beverages: HashMap<String, HashMap<String, Int>>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Outlets (

        val count_n: Int
)

