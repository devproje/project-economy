package net.projecttl.economy.plugin.commands

import net.projecttl.economy.plugin.instance
import net.projecttl.economy.plugin.utils.openExchangeGUI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ExchangeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (command.name == "exchanger") {
                if (args.isEmpty()) {
                    openExchangeGUI(sender, instance)
                    return true
                }
            }
        }
        return false
    }
}