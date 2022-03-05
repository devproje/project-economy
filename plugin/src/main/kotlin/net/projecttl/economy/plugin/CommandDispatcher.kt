package net.projecttl.economy.plugin

import net.kyori.adventure.text.Component
import net.projecttl.economy.plugin.utils.Economy
import net.projecttl.economy.plugin.utils.moneyUnit
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CommandDispatcher : CommandExecutor, TabCompleter {

    private enum class Arg { account, rank, send }
    private enum class ArgOP { add, unit, query, subtract, set }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val economy = if (sender is Player) Economy(sender)
        else null
        if (args.isEmpty()) {
            economy ?: return false
            sender.sendMessage("계정 잔고: §a${economy.money}$moneyUnit")
            return true
        }
        when (args.first()) {
            Arg.rank.name -> {
                economy ?: return false
                economy.getRanking()
                return true
            }
            Arg.send.name -> {
                economy ?: return false
                if (args.size != 3) {
                    sender.sendMessage("§c/$label ${args.first()} <PlayerName> <Amount>")
                    return true
                }
                val target = getPlayer(args[1])
                val amount = args.last().toIntOrNull()
                if (target == null) {
                    sender.sendMessage("§c대상을 찾을 수 없습니다.")
                    return true
                }
                if (amount == null) {
                    sender.sendMessage("§c${args.last()} 은/는 정수가 아닙니다.")
                    return true
                }

                if (!economy.subtractMoney(amount)) {
                    if (economy.money < amount) sender.sendMessage("§c잔액이 부족합니다.")
                    sender.sendMessage("§c${args.last()} 은/는 양수가 아닙니다.")
                    return true
                }

                val targetAccount = Economy(target)

                economy.subtractMoney(amount)
                targetAccount.addMoney(amount)

                sender.sendMessage("§e${sender.name} §a님 에게 §e${amount}§a${moneyUnit} 을/를 보냈습니다.")
                target.sendMessage("§e${sender.name} §a님 으로부터 §e${amount}§a${moneyUnit}을/를 받았습니다.")

                return true
            }
            Arg.account.name -> {
                economy ?: return false
                economy.showAccount()
                return true
            }
            ArgOP.add.name -> {
                if (!sender.isOp) return true
                if (args.size != 3) {
                    sender.sendMessage("§c/$label ${args.first()} <PlayerName> <Amount>")
                    return true
                }
                val target = getPlayer(args[1])
                val amount = args.last().toIntOrNull()
                if (target == null) {
                    sender.sendMessage("§c대상을 찾을 수 없습니다.")
                    return true
                }
                if (amount == null) {
                    sender.sendMessage("§c${args.last()} 은/는 정수가 아닙니다.")
                    return true
                }

                val targetAccount = Economy(target)

                if (!targetAccount.addMoney(amount)) {
                    sender.sendMessage("§c${args.last()} 은/는 양수가 아닙니다.")
                    return true
                }

                sender.sendMessage("§e${sender.name} §a님 에게 §e${amount}§a${moneyUnit} 을/를 추가 했습니다.")
                target.sendMessage("§e관리자§a로부터 §e${amount}§a${moneyUnit}을/를 받았습니다.")

                return true
            }
            ArgOP.subtract.name -> {
                if (!sender.isOp) return true
                if (args.size != 3) {
                    sender.sendMessage("§c/$label ${args.first()} <PlayerName> <Amount>")
                    return true
                }
                val target = getPlayer(args[1])
                val amount = args.last().toIntOrNull()

                if (target == null) {
                    sender.sendMessage("§c대상을 찾을 수 없습니다.")
                    return true
                }
                if (amount == null) {
                    sender.sendMessage("§c${args.last()} 은/는 정수가 아닙니다.")
                    return true
                }

                val targetAccount = Economy(target)
                targetAccount.subtractMoney(amount)

                sender.sendMessage("§a설정된 §e${sender.name}§a의 계정 잔액은 ${amount}${moneyUnit}입니다.")

                return true
            }
            ArgOP.set.name -> {
                if (!sender.isOp) return true
                if (args.size != 3) {
                    sender.sendMessage("§c/$label ${args.first()} <PlayerName> <Amount>")
                    return true
                }
                val target = getPlayer(args[1])
                val amount = args.last().toIntOrNull()

                if (target == null) {
                    sender.sendMessage("§c대상을 찾을 수 없습니다.")
                    return true
                }
                if (amount == null) {
                    sender.sendMessage("§c${args.last()} 은/는 정수가 아닙니다.")
                    return true
                }

                val targetAccount = Economy(target)

                if (amount < 0) {
                    sender.sendMessage("§c${args.last()} 은/는 양수가 아닙니다.")
                    return true
                }

                targetAccount.money = amount
                sender.sendMessage(
                    "§e${target.name} §a님의 계정 잔액이 §e${targetAccount.money}§a${moneyUnit}으/로 설정 되었습니다."
                )

                return true
            }
            ArgOP.query.name -> {
                if (!sender.isOp) return true
                economy ?: return false
                economy.queryList()
                return true
            }
            ArgOP.unit.name -> {
                if (args.size != 2) {
                    sender.sendMessage("§c/$label ${args.first()} <Unit>")
                    return true
                }
                val unit = args[1]
                if (!sender.isOp) return true

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
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        val players = Bukkit.getOnlinePlayers()
        if (args.size == 1) {
            for (s in Arg.values()) if (s.name.contains(args.last())) list.add(s.name)
            if (sender.isOp) for (s in ArgOP.values()) if (s.name.contains(args.last())) list.add(s.name)
            return list
        }
        if (args.size > 1) when (args.first()) {
            Arg.send.name -> {
                if (args.size == 2) for (player in players) if (player.name.contains(args.last())) list.add(player.name)
            }
            ArgOP.add.name,
            ArgOP.subtract.name,
            ArgOP.set.name -> {
                if (!sender.isOp) return list
                if (args.size == 2) for (player in players) if (player.name.contains(args.last())) list.add(player.name)
            }
            ArgOP.unit.name -> {
                if (!sender.isOp) return list
                if (args.size == 2) if (args.last() == "") list.add("<Unit>")
            }
            else -> return list
        }
        return list
    }
}
