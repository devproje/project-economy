package net.projecttl.peconomy.listeners

import net.projecttl.peconomy.api.Economy
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RegisterListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val system = Economy(player)
        system.registerAccount()
    }
}