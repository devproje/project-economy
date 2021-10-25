package net.projecttl.economy.plugin.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.projecttl.economy.plugin.instance
import net.projecttl.economy.plugin.utils.Economy
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class DiscordCommand : ListenerAdapter() {

    companion object {
        val pinList = mutableMapOf<Player, Int>()
        val map = mutableMapOf<Player, String>()
    }

    private fun randomPin(): Int {
        val rand = Math.random() * 10
        val first = rand.toInt() * 1000
        val second = rand.toInt() * 100
        val third = rand.toInt() * 10
        val forth = rand.toInt()

        return first + second + third + forth
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.user.isBot) {
            return
        }

        if (event.name == "account") {
            for (i in Bukkit.getOnlinePlayers()) {
                val economy = Economy(i)
                if (event.subcommandName == i.name) {
                    if (!i.isOnline) {
                        val embed = EmbedBuilder().let { embeds ->
                            embeds.setTitle(":octagonal_sign: Error!")
                            embeds.setDescription("${i.name} is not online!")
                            embeds.setFooter("${event.user.name}#${event.user.discriminator}", event.user.avatarUrl)
                            embeds.setColor(instance.config.getInt("EMBED_COLOR"))

                            embeds
                        }.build()

                        event.replyEmbeds(embed).setEphemeral(false).queue()
                    } else if (economy.queryAccount().keys.contains(i.uniqueId)) {
                        val embed = EmbedBuilder().let { embeds ->
                            embeds.setTitle(":octagonal_sign: Error!")
                            embeds.setDescription("${i.name} is already merged!")
                            embeds.setFooter("${event.user.name}#${event.user.discriminator}", event.user.avatarUrl)
                            embeds.setColor(instance.config.getInt("EMBED_COLOR"))

                            embeds
                        }.build()

                        event.replyEmbeds(embed).setEphemeral(false).queue()
                    } else {
                        map[i] = event.user.id
                        i.sendMessage("${ChatColor.YELLOW}${event.user.name}#${event.user.discriminator} has send merge request in this minecraft account.\n")
                        i.sendMessage("${ChatColor.GOLD}If you not want merge account, please type command " +
                                "${ChatColor.RESET}'/merge ignore'${ChatColor.GOLD}.")
                        pinList[i] = randomPin()

                        i.sendMessage("Pin Code: ${ChatColor.GREEN}${pinList[i]}")
                    }
                }
            }
        }
    }
}