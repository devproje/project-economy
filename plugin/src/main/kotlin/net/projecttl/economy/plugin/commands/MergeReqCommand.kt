package net.projecttl.economy.plugin.commands

import net.projecttl.economy.plugin.utils.Economy
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object MergeReqCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (command.name == "merge") {
                val economy = Economy(sender)
                if (args.isEmpty()) {
                    return false
                } else {
                    when (args[0]) {
                        "accept" -> {
                            val pin = Integer.parseInt(args[1])
                            val checkPin = DiscordCommand.pinList[sender]

                            if (checkPin == pin) {
                                economy.mergeDiscordID(DiscordCommand.map[sender].toString())
                            }

                            DiscordCommand.pinList.remove(sender)
                            DiscordCommand.map.remove(sender)
                        }

                        "ignore" -> {

                        }
                    }
                }
            }
        }

        return false
    }
}