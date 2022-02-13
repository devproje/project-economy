package net.projecttl.economy.plugin

import net.kyori.adventure.text.Component
import net.projecttl.economy.plugin.utils.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CommandDispatcher : CommandExecutor, TabCompleter {

    private fun getPlayer(argument: String): Player {
        return Bukkit.getPlayer(argument)!!
    }

    private fun Player.checkPerm(): Boolean {
        if (!this.isOp) {
            this.sendMessage("§c접근 권한이 없습니다.")
            return false
        }

        return true
    }

    private fun Player.getOnline(): Boolean {
        if (!this.isOnline) {
            this.sendMessage("§e${this.name}§c은(는) 온라인이 아닙니다.")
            return false
        }

        return true
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when (command.name) {
            "money" -> {
                if (sender is Player) {
                    val economy = Economy(sender)
                    if (args.isEmpty()) {
                        sender.sendMessage("계정 잔고: §a${economy.money}$moneyUnit")
                        return true
                    }

                    when (args[0]) {
                        "send" -> {
                            val target = getPlayer(args[1])
                            val amount = Integer.parseInt(args[2])

                            val targetAccount = Economy(target)
                            if (!target.getOnline()) return true

                            if (economy.money < amount) {
                                sender.sendMessage("§c당신이 가진 금액보다 더 많이 보낼 수 없습니다.")
                                return true
                            } else if (economy.money <= 0 || amount <= 0) {
                                sender.sendMessage("§c잔액은 0${moneyUnit}보다 작아서는 안 됩니다.")
                                return true
                            }

                            economy.subtractMoney(amount)
                            targetAccount.addMoney(amount)

                            sender.sendMessage("§e${sender.name}§a에게 ${amount}${moneyUnit}이(가) 전송 되었습니다.")
                            target.sendMessage("§e${sender.name}로부터 ${amount}${moneyUnit}을(를) 받았습니다.")

                            return true
                        }
                        "rank" -> {
                            economy.getRanking()
                            return true
                        }
                        "account" -> {
                            economy.showAccount()
                            return true
                        }
                        "add" -> {
                            if (!sender.checkPerm()) return true

                            val target = getPlayer(args[1])
                            val amount = Integer.parseInt(args[2])

                            val targetAccount = Economy(target)

                            if (amount < 0) {
                                sender.sendMessage("§c잔액은 0${moneyUnit}보다 작아서는 안 됩니다.")
                                return true
                            }

                            if (!target.getOnline()) {
                                return true
                            }

                            targetAccount.addMoney(amount)
                            sender.sendMessage(
                                "§a설정된 §e${target.name}§a의 계정 잔액은 ${targetAccount.money}${moneyUnit}입니다."
                            )

                            return true
                        }
                        "subtract" -> {
                            if (!sender.checkPerm()) return true

                            val target = getPlayer(args[1])
                            val amount = Integer.parseInt(args[2])

                            val targetAccount = Economy(target)

                            if (amount < 0) {
                                sender.sendMessage("§c잔액은 0${moneyUnit}보다 작아서는 안 됩니다.")
                                return true
                            }

                            if (!target.getOnline()) {
                                return true
                            }

                            targetAccount.subtractMoney(amount)
                            sender.sendMessage("§a설정된 §e${sender.name}§a의 계정 잔액은 ${amount}${moneyUnit}입니다.")

                            return true
                        }
                        "set" -> {
                            if (!sender.checkPerm()) return true

                            val target = getPlayer(args[1])
                            val amount = Integer.parseInt(args[2])

                            val targetAccount = Economy(target)

                            if (amount < 0) {
                                sender.sendMessage("§c잔액은 0${moneyUnit}보다 작아서는 안 됩니다.")
                                return true
                            }

                            if (!target.getOnline()) return true

                            targetAccount.money = amount
                            sender.sendMessage(
                                "§a설정된 §e${target.name}§a의 계정 잔액은 ${targetAccount.money}${moneyUnit}입니다."
                            )

                            return true
                        }
                        "query" -> {
                            if (!sender.checkPerm()) return true

                            economy.queryList()
                            return true
                        }
                        "moneyunit" -> {
                            val unit = args[1]
                            if (!sender.checkPerm()) return true
                        
                            if (unit == "") {
                                sender.sendMessage("§c화폐 단위는 공백일 수 없습니다.")
                                return true
                            }
                        
                            instance.config.set("MONEY_UNIT", unit)
                            Bukkit.broadcast(
                                Component.text(
                                    """
                                        §a이제 서버 화폐 단위는 다음과 같습니다: §f<unit>
                                        §6화폐 단위를 적용하려면 reload 명령을 입력하거나 서버를 다시 시작하십시오.
                                    """.trimIndent()
                                )
                            )
                    
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val arr = mutableListOf<String>()

        when (command.name) {
            "money" -> {
                when (args.size) {
                    0 -> return null
                    1 -> {
                        arr.add("account")
                        arr.add("add")
                        arr.add("moneyunit")
                        arr.add("query")
                        arr.add("rank")
                        arr.add("remove")
                        arr.add("send")
                        arr.add("set")
                        arr.add("lang")

                        return arr
                    }
                }
            }
        }

        return null
    }
}
