package net.projecttl.pbalance.listeners

import net.projecttl.pbalance.api.Economy
import net.projecttl.pbalance.api.moneyUnit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RegisterListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val system = Economy(player)
        system.registerAccount()

        player.sendMessage("""
            ${ChatColor.GOLD}================================
            ${ChatColor.WHITE}Hello, ${player.name}!
            Your balance is ${system.money}${moneyUnit()}
            Good luck!
            ${ChatColor.GOLD}================================
        """.trimIndent())
    }
}