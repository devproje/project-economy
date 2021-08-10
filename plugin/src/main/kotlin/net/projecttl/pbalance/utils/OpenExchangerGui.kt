package net.projecttl.pbalance.utils

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import net.projecttl.pbalance.PBalance
import net.projecttl.pbalance.api.Economy
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun openGui(plugin: PBalance, player: Player) {
    player.openInventory(plugin.gui(InventoryType.CHEST_45, Component.text("${ChatColor.GREEN}SHOP")) {
        // Food Stuffs
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

        val carrot = ItemStack(Material.CARROT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GOLD}Sell CARROT")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("carrot")}₩")

            it.itemMeta = itemMeta
            it
        }

        val beetroot = ItemStack(Material.BEETROOT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.RED}Sell Beetroot")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("beetroot")}₩")

            it.itemMeta = itemMeta
            it
        }

        val melonSlice = ItemStack(Material.MELON_SLICE).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.DARK_GREEN}Sell Melon Slice")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("melon_slice")}₩")

            it.itemMeta = itemMeta
            it
        }

        val sugarCane = ItemStack(Material.SUGAR_CANE).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GREEN}Sell Sugar Cane")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("sugar_cane")}₩")

            it.itemMeta = itemMeta
            it
        }

        val pumpkin = ItemStack(Material.PUMPKIN).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GOLD}Sell Pumpkin")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("pumpkin")}₩")

            it.itemMeta = itemMeta
            it
        }

        // Mineshaft Material
        val stone = ItemStack(Material.COBBLESTONE).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GRAY}Sell Cobblestone")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("cobblestone")}₩")

            it.itemMeta = itemMeta
            it
        }

        val copper = ItemStack(Material.COPPER_INGOT).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.GOLD}Sell Copper Ingot")
            itemMeta?.lore = listOf("${ChatColor.GREEN}${plugin.config.getInt("copper")}₩")

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

        for (i in 0..44) {
            slot(i, voidGlass) {
                this.isCancelled = true
            }
        }


        for (i in 10..16) {
            slot(i, ItemStack(Material.AIR))
        }

        for (i in 19..25) {
            slot(i, ItemStack(Material.AIR))
        }

        /*
        for (i in 28..34) {
            slot(i, ItemStack(Material.AIR))
        }
         */

        slot(4, checkBalance) {
            val moneySystem = Economy(player)
            player.sendMessage("${ChatColor.GREEN}CURRENT BALANCE: ${ChatColor.WHITE}${moneySystem.money}₩")
            this.isCancelled = true
        }

        slot(44, exitItem) {
            player.closeInventory()
            this.isCancelled = true
        }

        // First Line
        slot(10, wheat) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.WHEAT, 1), plugin.config.getInt("wheat"))
            }

            this.isCancelled = true
        }

        slot(11, potato) {
            val moneySystem = Economy(this.whoClicked as Player)
            moneySystem.sellWithItem(ItemStack(Material.POTATO), plugin.config.getInt("potato"))
            this.isCancelled = true
        }

        slot(12, carrot) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.CARROT), plugin.config.getInt("carrot"))
            }

            this.isCancelled = true
        }

        slot(13, beetroot) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.BEETROOT), plugin.config.getInt("beetroot"))
            }

            this.isCancelled = true
        }

        slot(14, melonSlice) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.MELON_SLICE), plugin.config.getInt("melon_slice"))
            }

            this.isCancelled = true
        }

        slot(15, sugarCane) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.SUGAR_CANE), plugin.config.getInt("sugar_cane"))
            }

            this.isCancelled = true
        }

        slot(16, pumpkin) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.PUMPKIN), plugin.config.getInt("pumpkin"))
            }

            this.isCancelled = true
        }

        // Second Line
        slot(19, copper) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.COPPER_INGOT), plugin.config.getInt("copper"))
            }

            this.isCancelled = true
        }

        slot(20, stone) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.COBBLESTONE), plugin.config.getInt("cobblestone"))
            }

            this.isCancelled = true
        }

        slot(21, iron) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.IRON_INGOT, 1), plugin.config.getInt("iron_ingot"))
            }

            this.isCancelled = true
        }

        slot(22, gold) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.GOLD_INGOT, 1), plugin.config.getInt("gold_ingot"))
            }

            this.isCancelled = true
        }

        slot(23, diamond) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.DIAMOND, 1), plugin.config.getInt("diamond"))
            }

            this.isCancelled = true
        }

        slot(24, netherite) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.NETHERITE_INGOT), plugin.config.getInt("netherite"))
            }

            this.isCancelled = true
        }

        slot(25, emerald) {
            val moneySystem = Economy(this.whoClicked as Player)
            if (this.isLeftClick) {
                moneySystem.sellWithItem(ItemStack(Material.EMERALD, 1), plugin.config.getInt("emerald"))
            }

            this.isCancelled = true
        }
    })
}