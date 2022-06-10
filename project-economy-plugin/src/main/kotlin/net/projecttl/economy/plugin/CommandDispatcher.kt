package net.projecttl.economy.plugin

import io.github.monun.kommand.PluginKommand
import io.github.monun.kommand.getValue
import net.projecttl.economy.core.Economy
import org.bukkit.Bukkit

object CommandDispatcher {
    fun register(kommand: PluginKommand) {
        kommand.register("money") {
            then("send") {
                requires { isPlayer }
                executes { context ->
                    val userName: String by context
                    val target = try {
                        Bukkit.getPlayer(userName)
                    } catch (exception: Exception) {
                        return@executes
                    }
                }
            }
            requires { isPlayer }
            executes {
                val economy = Economy(player)
                economy.showAccount()
            }
        }
    }
}
