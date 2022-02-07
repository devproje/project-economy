package net.projecttl.economy.plugin

import net.kyori.adventure.text.Component
import net.projecttl.economy.plugin.utils.Economy
import net.projecttl.economy.plugin.utils.moneyUnit
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CommandDispatcher : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name == "money") {
            if (sender is Player) {
                val economy = Economy(sender)
                instance.connect()

                if (args.isEmpty()) {
                    sender.sendMessage("Your account balance: ${ChatColor.GREEN}${economy.money}${moneyUnit()}")
                    return false
                } else {
                    if (args[0] == "send") {
                        val name = args[0]
                        val amount = Integer.parseInt(args[1])

                        val target = Bukkit.getPlayer(name)
                        val targetAccount = Economy(target!!)

                        if (!target.isOnline) {
                            sender.sendMessage("${ChatColor.GOLD}${target.name} is offline!")
                        } else {
                            if (economy.money < amount) {
                                sender.sendMessage("${ChatColor.RED}You cannot send more than the amount you have.")
                            } else if (economy.money <= 0 || amount <= 0) {
                                sender.sendMessage("${ChatColor.RED}Balance must not be less of 0${moneyUnit()}")
                            } else {
                                economy.subtractMoney(amount)
                                targetAccount.addMoney(amount)

                                sender.sendMessage("You have successfully sent ${amount}${moneyUnit()} to ${target.name}")
                                target.sendMessage("You received $amount${moneyUnit()} from ${sender.name}")
                            }
                        }

                        return true
                    } else if (args[0] == "rank") {
                        economy.getRanking()
                        return true
                    } else if (args[0] == "account") {
                        instance.connect()
                        economy.showAccount()

                        return true
                    } else if (args[0] == "add") {
                        instance.connect()
                        if (sender.isOp) {
                            val name = args[1]
                            val amount = Integer.parseInt(args[2])

                            val target = Bukkit.getPlayer(name)
                            val targetAccount = Economy(target!!)

                            if (amount >= 0) {
                                if (!target.isOnline) {
                                    sender.sendMessage("${ChatColor.GOLD}${target.name} is offline!")
                                } else {
                                    targetAccount.addMoney(amount)
                                    sender.sendMessage("${ChatColor.GREEN}Now ${target.name}'s account balance is $amount${moneyUnit()}")
                                }
                            } else {
                                sender.sendMessage("${ChatColor.RED}Balance must not be less of 0${moneyUnit()}")
                            }
                        }

                        return true
                    } else if (args[0] == "remove") {
                        instance.connect()
                        if (sender.isOp) {
                            val name = args[1]
                            val amount = Integer.parseInt(args[2])

                            if (amount >= 0) {
                                val target = Bukkit.getPlayer(name)
                                val targetAccount = Economy(target!!)

                                if (!target.isOnline) {
                                    sender.sendMessage("${ChatColor.GOLD}${target.name} is offline!")
                                } else {
                                    targetAccount.subtractMoney(amount)
                                    sender.sendMessage("${ChatColor.GREEN}Now ${target.name}'s account balance is $amount${moneyUnit()}")
                                }
                            } else {
                                sender.sendMessage("${ChatColor.RED}Balance must not be less of 0${moneyUnit()}")
                            }
                        }

                        return true
                    } else if (args[0] == "set") {
                        instance.connect()
                        if (sender.isOp()) {
                            val name = args[1]
                            val amount = Integer.parseInt(args[2])

                            if (amount >= 0) {
                                val target = Bukkit.getPlayer(name)
                                val targetAccount = Economy(target!!)

                                if (!target.isOnline) {
                                    sender.sendMessage("${ChatColor.GOLD}${target.name} is offline!")
                                } else {
                                    targetAccount.money = amount
                                    sender.sendMessage("${ChatColor.GREEN}Now ${target.name}'s account balance is $amount${moneyUnit()}")
                                }
                            } else {
                                sender.sendMessage("${ChatColor.RED}Balance must not be less of 0${moneyUnit()}")
                            }
                        } else {
                            sender.sendMessage("${ChatColor.RED}You're not OP!")
                        }

                        return true
                    } else if (args[0] == "query") {
                        instance.connect()
                        if (sender.isOp) {
                            economy.queryList()
                        } else {
                            sender.sendMessage("${ChatColor.RED}You're not OP!")
                        }

                        return true
                    } else if (args[0] == "moneyunit") {
                        instance.connect()
                        val unit = args[1]
                        if (sender.isOp()) {
                            if (unit == "") {
                                sender.sendMessage("${ChatColor.RED}Money Unit must not be null!")
                            } else {
                                instance.config.set("MONEY_UNIT", unit)
                                Bukkit.broadcast(Component.text("${ChatColor.GREEN}Now your server money unit is this: ${ChatColor.WHITE}$unit"))
                                Bukkit.broadcast(Component.text("${ChatColor.GOLD}If you wanna change money unit, please type reload command or restart server."))
                            }
                        } else {
                            sender.sendMessage("${ChatColor.RED}You're not OP!")
                        }

                        return true
                    }
                }
            }
        }

        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val arr = mutableListOf<String>()

        if (args.size == 1) {
            arr.add("account")
            arr.add("add")
            arr.add("moneyunit")
            arr.add("query")
            arr.add("rank")
            arr.add("remove")
            arr.add("send")
            arr.add("set")

            return arr
        }

        return null
    }
}
