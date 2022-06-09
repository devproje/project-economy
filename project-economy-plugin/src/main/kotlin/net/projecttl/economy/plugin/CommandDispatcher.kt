package net.projecttl.economy.plugin

import io.github.monun.kommand.PluginKommand
import net.projecttl.economy.core.Economy

object CommandDispatcher {
    fun register(kommand: PluginKommand) {
        kommand.register("money") {
            requires { isPlayer }
            executes {
                val economy = Economy(player)
                economy.showAccount()
            }
        }
    }
}
