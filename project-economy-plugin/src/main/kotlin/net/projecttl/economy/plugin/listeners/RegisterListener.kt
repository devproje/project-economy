package net.projecttl.economy.plugin.listeners

import net.projecttl.economy.core.Economy
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RegisterListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val economy = Economy(event.player)
        economy.addAccount()
    }
}