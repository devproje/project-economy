package net.projecttl.peconomy.utils

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import net.projecttl.peconomy.PEconomy
import net.projecttl.peconomy.api.Economy
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun openGui(plugin: PEconomy, player: Player) {
    val enable: Boolean = plugin.config.getBoolean("USE_DEFAULT_EXCHANGE")
    if (enable) {
        player.openInventory(plugin.gui(InventoryType.CHEST_27, Component.text("${ChatColor.GREEN}SHOP")) {
            val moneySystem = Economy(player)
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
                itemMeta?.lore = listOf("${ChatColor.GREEN}CURRENT BALANCE: ${ChatColor.WHITE}${moneySystem.money}₩")

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
                slot(i, voidGlass)
            }

            slot(4, checkBalance)
            slot(26, exitItem) {
                player.closeInventory()
            }

            slot(10, wheat) {
                if (!player.inventory.contains(ItemStack(Material.WHEAT))) {
                    player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough wheat.")
                } else {
                    player.inventory.removeItem(ItemStack(Material.WHEAT, 1))
                    moneySystem.addMoney(plugin.config.getInt("wheat"))
                }
            }

            slot(11, potato) {
                if (!player.inventory.contains(ItemStack(Material.POTATO))) {
                    player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough potato.")
                } else {
                    player.inventory.removeItem(ItemStack(Material.POTATO, 1))
                    moneySystem.addMoney(plugin.config.getInt("potato"))
                }
            }

            slot(12, iron) {
                if (!player.inventory.contains(ItemStack(Material.IRON_INGOT))) {
                    player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough iron ingot.")
                } else {
                    player.inventory.removeItem(ItemStack(Material.IRON_INGOT, 1))
                    moneySystem.addMoney(plugin.config.getInt("iron_ingot"))
                }
            }

            slot(13, gold) {
                if (!player.inventory.contains(ItemStack(Material.GOLD_INGOT))) {
                    player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough gold ingot.")
                } else {
                    player.inventory.removeItem(ItemStack(Material.GOLD_INGOT, 1))
                    moneySystem.addMoney(plugin.config.getInt("gold_ingot"))
                }
            }

            slot(14, diamond) {
                if (!player.inventory.contains(ItemStack(Material.DIAMOND))) {
                    player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough diamond.")
                } else {
                    player.inventory.removeItem(ItemStack(Material.DIAMOND, 1))
                    moneySystem.addMoney(plugin.config.getInt("diamond"))
                }
            }

            slot(15, netherite) {
                if (!player.inventory.contains(ItemStack(Material.NETHERITE_INGOT))) {
                    player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough netherite ingot.")
                } else {
                    player.inventory.removeItem(ItemStack(Material.NETHERITE_INGOT, 1))
                    moneySystem.addMoney(plugin.config.getInt("netherite"))
                }
            }

            slot(16, emerald) {
                if (this.isLeftClick) {
                    if (!player.inventory.contains(ItemStack(Material.EMERALD))) {
                        player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough emerald.")
                    } else {
                        player.inventory.removeItem(ItemStack(Material.EMERALD, 1))
                        moneySystem.addMoney(plugin.config.getInt("emerald"))
                    }
                } else if (this.isRightClick) {
                    if (plugin.config.getInt("emerald") > moneySystem.money) {
                        player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough money.")
                    } else {
                        player.inventory.addItem(ItemStack(Material.EMERALD, 1))
                        moneySystem.removeMoney(plugin.config.getInt("emerald"))
                    }
                }
            }
        })
    }
}