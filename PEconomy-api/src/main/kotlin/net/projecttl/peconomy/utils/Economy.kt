package net.projecttl.peconomy.utils

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class Economy(private val plugin: Plugin, private val player: Player, private val target: Player) {

    var money: Int
        get() = InitSQLDriver.sqlConnection.prepareStatement("select amount from account(username, uuid) "
                + "where username = ${target.name} and uuid = ${target.uniqueId}").executeQuery() as Int

        set(amount) {
            InitSQLDriver.sqlConnection.prepareStatement("update account " +
                    "set amount = $amount where username = ${target.name} or uuid = ${target.uniqueId};").executeUpdate()
        }

    // TODO Create account
}