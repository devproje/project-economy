package net.projecttl.peconomy.commands

import net.projecttl.peconomy.utils.Economy
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class MoneyCommand(private val plugin: Plugin): CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name == "peconomy") {
            val target = args[1]
            val amount = Integer.parseInt(args[2])

            val targetPlayer = Bukkit.getPlayer(target)
            val economySystem = Economy(plugin, sender as Player, targetPlayer!!)

            return when (args[0]) {
                "set" -> {
                    economySystem.money = amount
                    true
                }

                "get" -> {
                    return if (args[1].isNotEmpty()) {
                        sender.sendMessage("${targetPlayer.name}'s account balance: ${ChatColor.GREEN}${economySystem.money}")
                        true
                    } else {
                        val economy = Economy(plugin, sender, sender)
                        sender.sendMessage("Your account balance: ${ChatColor.GREEN}${economy.money}")

                        true
                    }
                }

                "account" -> {
                    sender.sendMessage("${targetPlayer.name}'s account")
                    sender.sendMessage("UUID: ${targetPlayer.uniqueId}")
                    sender.sendMessage("Balance: ${economySystem.money}")
                    true
                }

                else -> false
            }
        }

        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if (command.name == "peconomy") {
            val commandList = mutableListOf<String>()

            when (args.size) {
                1 -> {
                    commandList.add("set")
                    commandList.add("get")
                    commandList.add("account")
                    return commandList
                }
            }
        }

        return null
    }
}