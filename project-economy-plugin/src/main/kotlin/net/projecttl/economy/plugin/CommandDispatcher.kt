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
                sender.sendMessage(
                    Component.text("온라인 플레이어 이름을 입력해 주세요!", NamedTextColor.RED)
                )
                return false
            }
            val target = Economy(player)
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
                    res = target.addMoney(amount)
                    if (!res) {
                        sender.sendMessage(Component.text("0 또는 음수 값을 입력하실 수 없습니다.", NamedTextColor.RED))
                    }

                    true
                }

                "drop" -> {
                    res = target.dropMoney(amount)
                    if (!res) {
                        sender.sendMessage(
                            Component.text("0 또는 음수 값 보유 계좌보다 더 큰 금액을 뺄 수 없습니다.", NamedTextColor.RED)
                        )
                    }

                    true
                }

                "set" -> {
                    target.money = amount
                    player.sendMessage(Component
                        .text("${target.player}", NamedTextColor.YELLOW)
                        .append(Component.text("님의 계좌를 ", NamedTextColor.GREEN))
                        .append(Component.text("${target.money}${moneyUnit}으로 설정 하였습니다.", NamedTextColor.GREEN)))

                    true
                }

                "send" -> {
                    if (sender !is Player) {
                        sender.sendMessage(
                            Component.text("이 명령어는 오직 플레이어만 사용할 수 있습니다.", NamedTextColor.RED)
                        )

                        return false
                    }

                    val me = Economy(sender)

                    val meRes = me.dropMoney(amount)
                    if (!meRes) {
                        me.player.sendMessage(
                            Component.text("잔고가 없거나 보낼 금액이 0 또는 음수 입니다.", NamedTextColor.RED)
                        )
                        return true
                    }

                    target.addMoney(amount)

                    me.player.sendMessage(Component.text(target.player.name, NamedTextColor.YELLOW).append(
                        Component.text("님에게 $amount${moneyUnit}을(를) 보냈습니다.", NamedTextColor.GREEN))
                    )

                    target.player.sendMessage(Component.text(me.player.name, NamedTextColor.YELLOW).append(
                        Component.text("님에게 $amount${moneyUnit}을(를) 받았습니다.", NamedTextColor.GREEN))
                    )

                    true
                }

                else -> false
            }
        }

        return true
    }
}
