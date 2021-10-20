package net.projecttl.economy.plugin

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

fun openExchangeGUI(player: Player, plugin: JavaPlugin) {
    player.gui(plugin, InventoryType.CHEST_27, Component.text("${ChatColor.GREEN}EXCHANGER")) {
        val voidItem = ItemStack(Material.BLACK_STAINED_GLASS_PANE).let {
            val itemMeta = it.itemMeta
            itemMeta.displayName(Component.text("${ChatColor.DARK_GRAY}VOID_ITEM"))
            it.itemMeta = itemMeta

            it
        }

        val emerald = ItemStack(Material.EMERALD).let {
            val itemMeta = it.itemMeta
            itemMeta.displayName(Component.text("${ChatColor.GREEN}EMERALD"))
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
    }
}