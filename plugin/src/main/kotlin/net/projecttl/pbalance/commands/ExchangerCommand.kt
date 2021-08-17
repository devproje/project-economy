package net.projecttl.pbalance.commands

import net.projecttl.pbalance.api.InitSQLDriver
import net.projecttl.pbalance.utils.ExchangeGUI
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class ExchangerCommand(private val plugin: Plugin): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (command.name == "exchanger") {
                return when {
                    args.isEmpty() -> {
                        return if (InitSQLDriver.exchangeGUI != true) {
                            sender.sendMessage("<PBalance> ${ChatColor.RED}Exchanger is not enabled!")
                            if (sender.isOp) {
                                sender.sendMessage("${ChatColor.GOLD}If you system manager, please type ${ChatColor.WHITE}/ex enable")
                            }

                            true
                        } else {
                            val guiSystem = ExchangeGUI(sender, plugin)
                            guiSystem.openExchange()

                            true
                        }
                    }

                    args.size == 1 -> {
                        return if (args[0] == "enable") {
                            if (InitSQLDriver.exchangeGUI != true) {
                                plugin.config.set("EXCHANGE_GUI", false)
                                sender.sendMessage("<PBalance> ${ChatColor.GREEN}Exchanger is successful enabled!")
                            } else {
                                plugin.config.set("EXCHANGE_GUI", true)
                                sender.sendMessage("<PBalance> ${ChatColor.GREEN}Exchanger is successful disabled!")
                            }

                            true
                        } else {
                            true
                        }
                    }

                    else -> false
                }
            }
        }

        return false
    }
}