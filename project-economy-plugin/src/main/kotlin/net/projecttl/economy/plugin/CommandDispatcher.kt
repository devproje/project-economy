package net.projecttl.economy.plugin

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.projecttl.economy.core.Economy
import net.projecttl.economy.core.utils.moneyUnit
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandDispatcher : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name == "money") return false
        if (sender is Player) {
            val money = Economy(sender)
            if (args.isEmpty()) {
                money.showAccount()
                return false
            }
        }

        // Manage commands
        if (args.isNotEmpty()) {
            val player: Player = try {
                Bukkit.getPlayer(args[1])!!
            } catch (exception: Exception) {
                sender.sendMessage(Component.text("온라인 플레이어 이름을 입력해 주세요!", NamedTextColor.RED))
                return false
            }
            val money = Economy(player)
            val amount: Int = try {
                Integer.parseInt(args[2])
            } catch (exception: Exception) {
                sender.sendMessage(Component.text("금액을 입력해 주시기 바랍니다.", NamedTextColor.RED))
                return false
            } catch (exception: IllegalStateException) {
                sender.sendMessage(Component.text("정수형 금액을 입력해 주시기 바랍니다.", NamedTextColor.RED))
                return false
            }
            val res: Boolean

            return when (args[0]) {
                "add" -> {
                    res = money.addMoney(amount)
                    if (!res) {
                        sender.sendMessage(Component.text("0 또는 음수 값을 입력하실 수 없습니다.", NamedTextColor.RED))
                    }

                    true
                }

                "drop" -> {
                    res = money.dropMoney(amount)
                    if (!res) {
                        sender.sendMessage(Component.text("0 또는 음수 값 보유 계좌보다 더 큰 금액을 뺄 수 없습니다.", NamedTextColor.RED))
                    }

                    true
                }

                "set" -> {
                    money.money = amount
                    player.sendMessage(Component
                        .text("${money.player}", NamedTextColor.YELLOW)
                        .append(Component.text("님의 계좌를 ", NamedTextColor.GREEN))
                        .append(Component.text("$money${moneyUnit}으로 설정 하였습니다.", NamedTextColor.GREEN)))

                    true
                }

                else -> false
            }
        }

        return true
    }
}
