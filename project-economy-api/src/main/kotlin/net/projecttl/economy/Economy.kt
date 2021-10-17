package net.projecttl.economy

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

private val moneyUnit: String = InitSQL.moneyUnits.toString()

fun moneyUnit(): String {
    return if (moneyUnit.length > 3) {
        " $moneyUnit"
    } else {
        moneyUnit
    }
}

class Economy(private val player: Player) {

    private val statement = InitSQL.sqlConnection.createStatement()

    var money: Int
        get() = queryMoney()

        set(amount) {
            statement.executeUpdate(
                "update ${InitSQL.dbName}.economy set balance = $amount " +
                        "where uuid = '${player.uniqueId}' or username = '${player.name}';")
        }

    fun addMoney(amount: Int) {
        money += amount
    }

    fun removeMoney(amount: Int) {
        if (amount > money) {
            player.sendMessage(
                "<PBalance> ${ChatColor.RED}Not enough balance in your account!\n" +
                        "${ChatColor.GREEN}Current balance${ChatColor.RESET}: ${money}${moneyUnit()}"
            )
        } else {
            money -= amount
        }
    }

    fun buy(item: ItemStack, amount: Int) {
        if (amount > money) {
            player.sendMessage(
                "<PBalance> ${ChatColor.RED}Not enough balance in your account!\n" +
                        "${ChatColor.GREEN}Current balance${ChatColor.RESET}: ${money}${moneyUnit()}")
        } else {
            removeMoney(amount)
            player.inventory.addItem(item)
            player.sendMessage(
                "<PBalance> ${ChatColor.GREEN}You're successful bought ${item.amount} ${item.itemMeta?.displayName().toString()}!\n" +
                        "${ChatColor.GREEN}Current balance${ChatColor.RESET}: ${money}${moneyUnit()}"
            )
        }
    }

    fun sell(item: ItemStack, amount: Int) {
        if (player.inventory.containsAtLeast(item, item.amount)) {
            player.inventory.removeItem(item)
            addMoney(amount)
            player.sendMessage(
                "<PBalance> ${ChatColor.GREEN}You're successful sell ${item.amount} ${item.itemMeta?.displayName().toString()}!\n" +
                        "${ChatColor.GREEN}Current balance${ChatColor.RESET}: ${money}${moneyUnit()}"
            )
        } else {
            player.sendMessage("<PBalance> ${ChatColor.RED}Not enough item!")
        }
    }

    fun getRanking() {
        val resultSets = statement.executeQuery(
            "SELECT * FROM ${InitSQL.dbName}.economy ORDER BY length(balance) desc, balance desc;"
        )
        var i = 1
        while (resultSets.next()) {
            when (i) {
                1 -> {
                    player.sendMessage("${i}st: ${resultSets.getString("username")} => ${resultSets.getInt("balance")}${moneyUnit()}")
                }

                2 -> {
                    player.sendMessage("${i}nd: ${resultSets.getString("username")} => ${resultSets.getInt("balance")}${moneyUnit()}")
                }

                3 -> {
                    player.sendMessage("${i}rd: ${resultSets.getString("username")} => ${resultSets.getInt("balance")}${moneyUnit()}")
                }

                else -> {
                    player.sendMessage("${i}th: ${resultSets.getString("username")} => ${resultSets.getInt("balance")}${moneyUnit()}")
                }
            }

            i++
        }
    }

    fun registerAccount() {
        statement.executeUpdate("insert ignore into ${InitSQL.dbName}.economy values('${player.uniqueId}', '${player.name}', 0);")
    }

    fun queryList() {
        val resultSets = statement.executeQuery("select * from ${InitSQL.dbName}.economy;")
        while (resultSets.next()) {
            player.sendMessage("${ChatColor.GOLD}===============================================\n" +
                    "${ChatColor.GREEN}UUID: ${ChatColor.RESET}${resultSets.getString("uuid")}\n" +
                    "${ChatColor.GREEN}NAME: ${ChatColor.RESET}${resultSets.getString("username")}\n" +
                    "${ChatColor.GREEN}BALANCE: ${ChatColor.RESET}${resultSets.getInt("balance")}${moneyUnit()}\n" +
                    "${ChatColor.GOLD}===============================================")
        }
    }

    private fun queryMoney(): Int {
        val resultSet = statement.executeQuery(
            "select * from ${InitSQL.dbName}.economy" +
                    " where username = '${player.name}' and uuid = '${player.uniqueId}'"
        )
        var result: Int? = null

        if (resultSet.next()) {
            result = resultSet.getInt("balance")
        }

        return result!!
    }
}