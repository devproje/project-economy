package net.projecttl.pbalance.api


private val moneyUnit: String = InitSQLDriver.moneyUnit.toString()

fun moneyUnit(): String {
    return if (moneyUnit.length > 3) {
        " $moneyUnit"
    } else {
        moneyUnit
    }
}