package net.projecttl.economy.plugin

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.projecttl.economy.core.Economy
import net.projecttl.economy.core.Economy.Companion.queryAccount
import net.projecttl.economy.core.utils.moneyUnit
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CommandDispatcher : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when (command.name) {
            "money" -> {
                if (sender is Player) {
                    if (args.isEmpty()) {
                        val acc = Economy(sender)
                        acc.showAccount(sender)

                        return false
                    }
                }

                if (!sender.isOp) return false
                if (args[0] == "query") {
                    sender.queryAccount()
                    return true
                }

                val res: Boolean
                val target: Player = try {
                    Bukkit.getPlayer(args[1])!!
                } catch (exception: Exception) {
                    sender.sendMessage(Component.text(
                        "이 서버에 있는 온라인 플레이어의 이름을 입력해 주세요.",
                        NamedTextColor.RED
                    ))
                    return false
                }
                val targetAcc = Economy(target)
                if (args[0] == "show") {
                    targetAcc.showAccount(sender)
                    return true
                }

                val amount = try {
                    Integer.parseInt(args[2])
                } catch (exception: Exception) {
                    sender.sendMessage(Component.text(
                        "정수형 값을 입력해 주세요.",
                        NamedTextColor.RED
                    ))
                    return false
                }

                when (args[0]) {
                    "add" -> {
                        res = targetAcc.addMoney(amount)
                        if (!res) {
                            sender.sendMessage(Component.text(
                                "0 또는 음수의 값을 추가할 수 없습니다.",
                                NamedTextColor.RED
                            ))
                        }

                        sender.sendMessage(Component.text(
                            targetAcc.player.name,
                            NamedTextColor.YELLOW
                        ).append(Component.text(
                            "님의 계좌에 ",
                            NamedTextColor.GREEN
                        )).append(Component.text(
                            "${amount}${moneyUnit}",
                            NamedTextColor.WHITE
                        )).append(Component.text(
                                "을(를) 추가 했습니다.",
                                NamedTextColor.GREEN
                        )))
                    }

                    "drop" -> {
                        res = targetAcc.dropMoney(amount)
                        if (!res) {
                            sender.sendMessage(Component.text(
                                "0 또는 음수의 값을 제거할 수 없습니다.",
                                NamedTextColor.RED
                            ))
                        }

                        sender.sendMessage(Component.text(
                            targetAcc.player.name,
                            NamedTextColor.YELLOW
                        ).append(Component.text(
                            "님의 계좌에 ",
                            NamedTextColor.GREEN
                        )).append(Component.text(
                            "${amount}${moneyUnit}",
                            NamedTextColor.WHITE
                        )).append(Component.text(
                            "을(를) 제거 했습니다.",
                            NamedTextColor.GREEN
                        )))
                    }

                    "set" -> {
                        targetAcc.money = amount
                        sender.sendMessage(Component.text(
                            targetAcc.player.name,
                            NamedTextColor.YELLOW
                        ).append(Component.text(
                            "님의 계좌를 ",
                            NamedTextColor.GREEN
                        )).append(Component.text(
                            "${amount}${moneyUnit}",
                            NamedTextColor.WHITE
                        )).append(Component.text(
                            "으로 설정 했습니다.",
                            NamedTextColor.GREEN
                        )))
                    }

                    else -> {
                        return false
                    }
                }

                true
            }

            "send" -> {
                if (sender !is Player) {
                    return true
                }

                val target: Player = try {
                    Bukkit.getPlayer(args[0])!!
                } catch (exception: Exception) {
                    return false
                }

                val amount: Int = try {
                    Integer.parseInt(args[1])
                } catch (exception: Exception) {
                    return false
                }

                val res: Boolean
                val acc = Economy(sender)
                val targetAcc = Economy(target)

                res = acc.dropMoney(amount)
                if (!res) {
                    sender.sendMessage(Component.text(
                        "잔액이 부족하거나 송금 값이 0 또는 음수입니다.",
                        NamedTextColor.RED
                    ))
                    return true
                }

                targetAcc.addMoney(amount)
                sender.sendMessage(Component.text(
                    targetAcc.player.name,
                    NamedTextColor.YELLOW
                ).append(Component.text(
                    "님에게 ",
                    NamedTextColor.GREEN
                )).append(Component.text(
                    "${amount}${moneyUnit}",
                    NamedTextColor.WHITE
                )).append(Component.text(
                    "을(를) 송금 하였습니다.",
                    NamedTextColor.GREEN
                )))
                targetAcc.player.sendMessage(Component.text(
                    acc.player.name,
                    NamedTextColor.YELLOW
                ).append(Component.text(
                    "님에게 ",
                    NamedTextColor.GREEN
                )).append(Component.text(
                    "${amount}${moneyUnit}",
                    NamedTextColor.WHITE
                )).append(Component.text(
                    "을(를) 받았습니다.",
                    NamedTextColor.GREEN
                )))

                true
            }

            "settings" -> {
                if (!sender.isOp) return false
                when (args[0]) {
                    "unit" -> {
                        val prefix = try {
                            args[1]
                        } catch (exception: ArrayIndexOutOfBoundsException) {
                            sender.sendMessage(Component.text(
                                "현재 사용되는 화폐 단위: ",
                                NamedTextColor.GREEN
                            ).append(Component.text(
                                moneyUnit,
                                NamedTextColor.WHITE
                            )))

                            return true
                        }

                        moneyUnit = prefix
                        sender.sendMessage(Component.text(
                            "화폐의 단위가 '",
                            NamedTextColor.GREEN
                        ).append(Component.text(moneyUnit, NamedTextColor.WHITE).append(Component.text(
                            "'으로 바뀌었습니다.",
                            NamedTextColor.GREEN
                        ))))

                        return true
                    }
                }

                true
            }

            else -> false
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        val arguments = mutableListOf<String>()
        return when (command.name) {
            "money" -> {
                if (!sender.isOp) return null
                if (args.size == 1) {
                    arguments += "add"
                    arguments += "drop"
                    arguments += "query"
                    arguments += "set"
                    arguments += "show"

                    return arguments
                }

                null
            }

            "settings" -> {
                if (args.size == 1) {
                    arguments += "unit"
                    return arguments
                }

                null
            }
            else -> null
        }
    }
}
