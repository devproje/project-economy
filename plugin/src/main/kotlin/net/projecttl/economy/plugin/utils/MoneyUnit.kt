package net.projecttl.economy.plugin.utils

import net.projecttl.economy.plugin.instance

var moneyUnit: String
    get() {
        val unit: String = instance.config.getString("MONEY_UNIT")!!

        return if (unit.length > 3) {
            " $unit"
        } else {
            unit
        }
    }
    set(value) {
        instance.config.set("MONEY_UNIT", value)
    }