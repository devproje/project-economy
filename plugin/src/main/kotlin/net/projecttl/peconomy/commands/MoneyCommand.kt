package net.projecttl.peconomy.commands

import net.projecttl.peconomy.PEconomy
import net.projecttl.peconomy.api.Economy
import net.projecttl.peconomy.utils.openGui
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class MoneyCommand(private val plugin: PEconomy): CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name == "peconomy") {
            if (args.isEmpty()) {
                return false
            }

            return when (args[0]) {
                "set" -> {
                    if (sender.isOp) {
                        val target = args[1]
                        val amount = Integer.parseInt(args[2])

                        if (amount >= 0) {
                            val targetPlayer = Bukkit.getPlayer(target)
                            val economySystem = Economy(targetPlayer!!)

                            if (!targetPlayer.isOnline) {
                                sender.sendMessage("<PEconomy> ${ChatColor.GOLD}${targetPlayer.name} is offline!")
                            } else {
                                economySystem.money = amount
                                sender.sendMessage("<PEconomy> ${ChatColor.GREEN}Now ${targetPlayer.name}'s account balance is ${amount}₩")
                            }
                        } else {
                            sender.sendMessage("<PEconomy> ${ChatColor.RED}Balance must not be less of 0₩")
                        }

                        true
                    } else {
                        sender.sendMessage("<PEconomy> ${ChatColor.RED}You're not OP!")
                        true
                    }
                }

                "query" -> {
                    if (sender.isOp) {
                        val moneySystem = Economy(sender as Player)
                        moneySystem.queryList()
                        true
                    } else {
                        sender.sendMessage("<PEconomy> ${ChatColor.RED}You're not OP!")
                        true
                    }
                }

                "send" -> {
                    val target = args[1]
                    val targetPlayer = Bukkit.getPlayer(target)
                    val amount = Integer.parseInt(args[2])

                    val senderMoney = Economy(sender as Player)
                    val targetMoney = Economy(targetPlayer!!)

                    if (senderMoney.money < amount) {
                        sender.sendMessage("<PEconomy> ${ChatColor.RED}You cannot send more than the amount you have.")
                    } else if (senderMoney.money <= 0 || amount <= 0) {
                        sender.sendMessage("<PEconomy> ${ChatColor.RED}Balance must not be less of 0₩")
                    } else {
                        targetMoney.addMoney(amount)
                        senderMoney.removeMoney(amount)

                        sender.sendMessage("<PEconomy> You have successfully sent ${amount}₩ to ${targetPlayer.name}")
                        targetPlayer.sendMessage("<PEconomy> You received ${amount}₩ from ${sender.name}")
                    }

                    true
                }

                "balance" -> {
                    val economy = Economy(sender as Player)
                    sender.sendMessage("Your account balance: ${ChatColor.GREEN}\$${economy.money}")

                    true
                }

                "account" -> {
                    val economySystem = Economy(sender as Player)

                    sender.sendMessage("${ChatColor.GOLD}==========[${ChatColor.RESET}${sender.name}'s account${ChatColor.GOLD}]==========")
                    sender.sendMessage("${ChatColor.YELLOW}UUID${ChatColor.RESET}: ${sender.uniqueId}")
                    sender.sendMessage("${ChatColor.YELLOW}Balance${ChatColor.RESET}: ${economySystem.money}₩")

                    true
                }

                else -> false
            }
        } else if (command.name == "exchange") {
            openGui(plugin, sender as Player)
            return true
        }

        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if (command.name == "peconomy") {
            val commandList = mutableListOf<String>()

            when (args.size) {
                1 -> {
                    commandList.add("set")
                    commandList.add("send")
                    commandList.add("balance")
                    commandList.add("account")
                    commandList.add("query")
                    commandList.add("exchange")
                    return commandList
                }

                2 -> {
                    if (args[0] == "set" && args[0] == "send") {
                        for (i in plugin.server.onlinePlayers) {
                            commandList.add(i.name)
                        }

                        return commandList
                    }
                }
            }
        }

        return null
    }
}