package net.projecttl.economy.plugin.listeners

import net.projecttl.economy.plugin.utils.Economy
import net.projecttl.economy.plugin.utils.moneyUnit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RegisterListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val economy = Economy(player)

        economy.registerAccount()
        player.sendMessage(
            """
                ${ChatColor.GOLD}================================
                ${ChatColor.WHITE}Hello, ${player.name}!
                Your balance is ${economy.money}${moneyUnit()}
                Good luck!
                ${ChatColor.GOLD}================================
                """.trimIndent()
        )
    }
}