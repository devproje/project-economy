package net.projecttl.economy.plugin.utils

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

fun openExchangeGUI(player: Player, plugin: JavaPlugin) {
    val economy = Economy(player)
    player.gui(plugin, InventoryType.CHEST_27, Component.text("${ChatColor.GREEN}EXCHANGER")) {
        val voidItem = ItemStack(Material.BLACK_STAINED_GLASS_PANE).let {
            val itemMeta = it.itemMeta
            itemMeta.displayName(Component.text("${ChatColor.DARK_GRAY}VOID_ITEM"))
            it.itemMeta = itemMeta

            it
        }

        val exitItem = ItemStack(Material.BARRIER).let {
            val meta = it.itemMeta
            meta.displayName(Component.text("${ChatColor.RED}Exit"))
            it.itemMeta = meta

            it
        }

        val compass = ItemStack(Material.COMPASS).let {
            val meta = it.itemMeta
            meta.displayName(Component.text("Check account"))
            meta.lore(listOf(Component.text("If you wanna see your account, please click me!")))
            it.itemMeta = meta

            it
        }

        val emerald = ItemStack(Material.EMERALD).let {
            val itemMeta = it.itemMeta
            itemMeta.displayName(Component.text("${ChatColor.GREEN}Emerald"))
            itemMeta.lore(
                listOf(
                    Component.text("${ChatColor.WHITE}Price: ${ChatColor.GREEN}${plugin.config.getInt("emerald_cost")}"),
                    Component.text("${ChatColor.WHITE}Buy: ${ChatColor.LIGHT_PURPLE}Left Click"),
                    Component.text("${ChatColor.WHITE}Buy 1 Set: ${ChatColor.LIGHT_PURPLE}Shift + Left Click"),
                    Component.text("${ChatColor.WHITE}Sell: ${ChatColor.LIGHT_PURPLE}Right Click"),
                    Component.text("${ChatColor.WHITE}Sell 1 Set: ${ChatColor.LIGHT_PURPLE}Shift + Right Click")
                )
            )
            it.itemMeta = itemMeta

            it
        }

        for (i in 0..8) {
            slot(i, voidItem)
        }

        for (i in 18..26) {
            slot(i, voidItem)
        }

        slot(9, voidItem)
        slot(17, voidItem)

        slot(26, exitItem) {
            close()
        }

        slot(4, compass) {
            economy.showAccount()
        }

        slot(10, emerald) {
            when (click) {
                ClickType.LEFT -> {
                    economy.buy(ItemStack(emerald.type), plugin.config.getInt("emerald_cost"))
                }

                ClickType.SHIFT_LEFT -> {
                    val item = ItemStack(emerald.type)
                    item.amount = 64

                    economy.buy(item, plugin.config.getInt("emerald_cost") * 64)
                }

                ClickType.RIGHT -> {
                    economy.sell(ItemStack(emerald.type), plugin.config.getInt("emerald_cost"))
                }

                ClickType.SHIFT_RIGHT -> {
                    val item = ItemStack(emerald.type)
                    item.amount = 64

                    economy.sell(item, plugin.config.getInt("emerald_cost") * 64)
                }
                else -> {}
            }
        }
    }
}