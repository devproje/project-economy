package net.projecttl.economy.plugin.utils

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.projecttl.economy.plugin.commands.DiscordCommand
import net.projecttl.economy.plugin.instance
import org.bukkit.Bukkit
import org.bukkit.ChatColor

class DiscordModule {

    private lateinit var jda: JDA

    init {
        if (!instance.config.getBoolean("BOT_ACTIVATE")) {
            instance.logger.fine("${ChatColor.GREEN}If you wanna discord bot connection, please BOT_ACTIVATE config value set ${ChatColor.LIGHT_PURPLE}'true'")
        } else {

            val builder = JDABuilder.createDefault(instance.config.getString("BOT_TOKEN")).apply {
                addEventListeners(DiscordCommand())
            }

            if (instance.config.getBoolean("ACTIVITY")) {
                builder.setActivity(Activity.playing(instance.config.getString("ACTIVITY_MESSAGE").toString()))
            }

            registerCommand()
        }
    }

    private fun registerCommand() {
        val accountCommand = jda.upsertCommand("account", "You can merge discord id with project-economy plugin")
        for (i in Bukkit.getOnlinePlayers()) {
            accountCommand.addOption(OptionType.SUB_COMMAND, i.name, "")
        }
    }
}