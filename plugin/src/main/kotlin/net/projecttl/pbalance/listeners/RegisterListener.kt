package net.projecttl.pbalance.listeners

import net.projecttl.pbalance.api.Economy
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