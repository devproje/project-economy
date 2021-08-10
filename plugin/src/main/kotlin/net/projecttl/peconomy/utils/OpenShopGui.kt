package net.projecttl.peconomy.utils

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import net.projecttl.peconomy.PEconomy
import net.projecttl.peconomy.api.Economy
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun openGui(plugin: PEconomy, player: Player) {
    player.openInventory(plugin.gui(InventoryType.CHEST_27, Component.text("${ChatColor.GREEN}SHOP")) {
        val wheat = ItemStack(Material.WHEAT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GREEN}Sell Wheat")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("wheat")}₩")

            it.itemMeta = itemMeta
            it
        }

        val potato = ItemStack(Material.POTATO).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.YELLOW}Sell Potato")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("potato")}₩")

            it.itemMeta = itemMeta
            it
        }

        val iron = ItemStack(Material.IRON_INGOT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.RESET}Sell Iron")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("iron_ingot")}₩")

            it.itemMeta = itemMeta
            it
        }

        val gold = ItemStack(Material.GOLD_INGOT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GOLD}Sell Gold")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("gold_ingot")}₩")

            it.itemMeta = itemMeta
            it
        }

        val diamond = ItemStack(Material.DIAMOND).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.AQUA}Sell Diamond")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("diamond")}₩")

            it.itemMeta = itemMeta
            it
        }

        val netherite = ItemStack(Material.NETHERITE_INGOT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.YELLOW}Sell Netherite")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("netherite")}₩")

            it.itemMeta = itemMeta
            it
        }

        val emerald = ItemStack(Material.EMERALD).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GREEN}Transfer Emerald")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("emerald")}₩")

            it.itemMeta = itemMeta
            it
        }

        val voidGlass = ItemStack(Material.BLACK_STAINED_GLASS_PANE).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("")
            it.itemMeta = itemMeta

            it
        }

        val checkBalance = ItemStack(Material.COMPASS).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GOLD}BALANCE MANAGER")
            itemMeta?.lore = listOf("${ChatColor.DARK_BLUE}Click Here")

            it.itemMeta = itemMeta
            it
        }

        val exitItem = ItemStack(Material.BARRIER).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.RED}Exit")
            itemMeta?.lore = listOf("${ChatColor.GREEN}If you wanna exit this gui, just.. click this!")

            it.itemMeta = itemMeta
            it
        }

        for (i in 0..26) {
            slot(i, voidGlass) {
                this.isCancelled = true
            }
        }

        slot(4, checkBalance) {
            val moneySystem = Economy(player)
            player.sendMessage("${ChatColor.GREEN}CURRENT BALANCE: ${ChatColor.WHITE}${moneySystem.money}₩")
            this.isCancelled = true
        }

        slot(26, exitItem) {
            player.closeInventory()
            this.isCancelled = true
        }

        slot(10, wheat) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.WHEAT, 1), plugin.config.getInt("wheat"))
            this.isCancelled = true
        }

        slot(11, potato) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.POTATO), plugin.config.getInt("potato"))
            this.isCancelled = true
        }

        slot(12, iron) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.IRON_INGOT, 1), plugin.config.getInt("iron_ingot"))
            this.isCancelled = true
        }

        slot(13, gold) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.GOLD_INGOT, 1), plugin.config.getInt("gold_ingot"))
            this.isCancelled = true
        }

        slot(14, diamond) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.DIAMOND, 1), plugin.config.getInt("diamond"))
            this.isCancelled = true
        }

        slot(15, netherite) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.NETHERITE_INGOT), plugin.config.getInt("netherite"))
            this.isCancelled = true
        }

        slot(16, emerald) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (this.isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.EMERALD, 1), plugin.config.getInt("emerald"))
            } else if (this.isRightClick) {
                moneySystem.exchangeWithItem(ItemStack(Material.EMERALD, 1), plugin.config.getInt("emerald"))
            }

            this.isCancelled = true
        }
    })
}