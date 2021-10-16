package net.projecttl.pbalance.utils

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import net.projecttl.pbalance.api.Economy
import net.projecttl.pbalance.api.moneyUnit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class ExchangeGUI(private val player: Player, private val plugin: Plugin) {

    fun openExchange() {
        val system = Economy(player)
        player.gui(plugin, InventoryType.CHEST_27, Component.text("${ChatColor.GREEN}EXCHANGER")) {
            val compass = ItemStack(Material.COMPASS).let {
                val itemMeta = it.itemMeta
                itemMeta?.displayName(Component.text("CHECKING MONEY"))
                itemMeta?.lore(listOf(Component.text("If you wanna see your account, click me!")))
                it.itemMeta = itemMeta

                it
            }

            val voidItem = ItemStack(Material.BLACK_STAINED_GLASS_PANE).let {
                val itemMeta = it.itemMeta
                itemMeta?.displayName(Component.text("${ChatColor.GRAY}VOID ITEM"))

                it.itemMeta = itemMeta
                it
            }

            val exitItem = ItemStack(Material.BARRIER).let {
                val itemMeta = it.itemMeta
                itemMeta?.displayName(Component.text("${ChatColor.RED}Exit"))
                itemMeta?.lore(listOf(Component.text("${ChatColor.WHITE}You can close this gui")))

                it.itemMeta = itemMeta
                it
            }

            for (i in 0..26) {
                slot(i, voidItem) {
                    isCancelled = true
                }
            }

            slot(4, compass) {
                player.sendMessage("Your account balance: ${ChatColor.GREEN}${system.money}${moneyUnit()}")
            }

            slot(26, exitItem) {
                player.closeInventory()
            }

            for (i in 11..16) {
                slot(i, moneyDesc()[i]!!) {
                    when (click) {
                        ClickType.RIGHT -> {
                            system.sell(money()[i]!!, amount()[i]!!)
                        }
                        ClickType.SHIFT_RIGHT -> {
                            system.sellAll(money()[i]!!, amount()[i]!!)
                        }
                        ClickType.LEFT -> {
                            system.buy(money()[i]!!, amount()[i]!!)
                        }
                        ClickType.SHIFT_LEFT -> {
                            system.buyAll(money()[i]!!, amount()[i]!!)
                        }
                    }

                    isCancelled = true
                }
            }

            slot(10, moneyDesc()[10]!!) {
                when (click) {
                    ClickType.RIGHT -> {
                        system.sell(ItemStack(Material.EMERALD), amount()[10]!!)
                    }
                    ClickType.SHIFT_RIGHT -> {
                        system.sellAll(ItemStack(Material.EMERALD), amount()[10]!!)
                    }
                    ClickType.LEFT -> {
                        system.buy(ItemStack(Material.EMERALD), amount()[10]!!)
                    }
                    ClickType.SHIFT_LEFT -> {
                        system.buyAll(ItemStack(Material.EMERALD), amount()[10]!!)
                    }
                }

                isCancelled = true
            }
        }
    }

    private fun createMoney(amount: Int): ItemStack {
        return ItemStack(Material.PAPER).let {
            val itemMeta = it.itemMeta
            itemMeta?.setDisplayName("${ChatColor.YELLOW}$amount${moneyUnit()}")
            itemMeta?.lore = listOf(
                "${ChatColor.GREEN}It's looks like a cash!",
                "${ChatColor.GOLD}Value: ${ChatColor.WHITE}$amount${moneyUnit()}"
            )
            itemMeta?.addEnchant(Enchantment.MENDING, 1, true)
            it.itemMeta = itemMeta
            it
        }
    }

    private fun money(): Map<Int, ItemStack> {
        val emerald = ItemStack(Material.EMERALD).let {
            val itemMeta = it.itemMeta
            itemMeta?.lore(listOf(
                Component.text("${ChatColor.GREEN}It is minecraft default money!"),
                Component.text("${ChatColor.GOLD}Value: ${ChatColor.WHITE}${amount()[10]}${moneyUnit()}")
            ))

            it.itemMeta = itemMeta
            it
        }

        val money1000   = createMoney(1000)
        val money5000   = createMoney(5000)
        val money10000  = createMoney(10000)
        val money50000  = createMoney(50000)
        val money100000 = createMoney(100000)
        val money500000 = createMoney(500000)

        return mapOf(
            10 to emerald,
            11 to money1000,
            12 to money5000,
            13 to money10000,
            14 to money50000,
            15 to money100000,
            16 to money500000
        )
    }

    private fun createMoneyDesc(amount: Int): ItemStack {
        return ItemStack(Material.PAPER).let {
            val itemMeta = it.itemMeta
            itemMeta?.displayName(Component.text("${ChatColor.YELLOW}$amount${moneyUnit()}"))
            itemMeta?.lore(listOf(
                Component.text("${ChatColor.GREEN}It's looks like a cash!"),
                Component.text("${ChatColor.GOLD}Value: ${ChatColor.WHITE}$amount${moneyUnit()}"),
                Component.text("\n"),
                Component.text("${ChatColor.GREEN}If you wanna buy or sell item, "),
                Component.text("${ChatColor.GREEN}please click Left Mouse or Right Mouse."),
                Component.text("${ChatColor.GOLD}If you wanna buy item in 1 set, please click 'Shift + Right'."),
                Component.text("${ChatColor.GOLD}If you wanna sell item in 1 set, please click 'Shift + Left'.")
            ))

            itemMeta?.addEnchant(Enchantment.MENDING, 1, true)
            it.itemMeta = itemMeta
            it
        }
    }

    private fun moneyDesc(): Map<Int, ItemStack> {
        val emerald = ItemStack(Material.EMERALD).let {
            val itemMeta = it.itemMeta
            itemMeta?.lore(listOf(
                Component.text("${ChatColor.GREEN}It is minecraft default money!"),
                Component.text("${ChatColor.GOLD}Value: ${ChatColor.WHITE}${amount()[10]}${moneyUnit()}"),
                Component.text("\n"),
                Component.text("${ChatColor.GREEN}If you wanna buy or sell item,"),
                Component.text("${ChatColor.GREEN}please click Left Mouse or Right Mouse."),
                Component.text("${ChatColor.GOLD}If you wanna buy item in 1 set, please click 'Shift + Right'."),
                Component.text("${ChatColor.GOLD}If you wanna sell item in 1 set, please click 'Shift + Left'.")
            ))

            it.itemMeta = itemMeta
            it
        }

        val money1000   = createMoneyDesc(1000)
        val money5000   = createMoneyDesc(5000)
        val money10000  = createMoneyDesc(10000)
        val money50000  = createMoneyDesc(50000)
        val money100000 = createMoneyDesc(100000)
        val money500000 = createMoneyDesc(500000)

        return mapOf(
            10 to emerald,
            11 to money1000,
            12 to money5000,
            13 to money10000,
            14 to money50000,
            15 to money100000,
            16 to money500000
        )
    }

    private fun amount(): Map<Int, Int> {
        return mapOf(
            10 to inlineEmerald,
            11 to 1000,
            12 to 5000,
            13 to 10000,
            14 to 50000,
            15 to 100000,
            16 to 500000
        )
    }

    private val inlineEmerald = plugin.config.getInt("emerald_cost")
}