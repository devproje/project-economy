package net.projecttl.pbalance.utils

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import net.projecttl.pbalance.api.Economy
import net.projecttl.pbalance.api.moneyUnit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class ExchangeGUI(private val player: Player, private val plugin: Plugin) {

    private val plantsItem = mapOf(
        10 to ItemStack(Material.WHEAT),
        11 to ItemStack(Material.POTATO),
        12 to ItemStack(Material.CARROT),
        13 to ItemStack(Material.SUGAR_CANE),
        14 to ItemStack(Material.PUMPKIN),
        15 to ItemStack(Material.MELON_SLICE),
        16 to ItemStack(Material.CAKE)
    )

    private val resourcesItem = mapOf(
        19 to ItemStack(Material.COBBLESTONE),
        20 to ItemStack(Material.COPPER_INGOT),
        21 to ItemStack(Material.IRON_INGOT),
        22 to ItemStack(Material.GOLD_INGOT),
        23 to ItemStack(Material.DIAMOND),
        24 to ItemStack(Material.NETHERITE_INGOT),
        25 to ItemStack(Material.EMERALD)
    )

    fun openExchange() {
        val system = Economy(player)
        player.gui(plugin, InventoryType.CHEST_45, Component.text("${ChatColor.GREEN}EXCHANGER")) {
            val voidItem = ItemStack(Material.BLACK_STAINED_GLASS_PANE).let {
                val itemMeta = it.itemMeta
                itemMeta?.setDisplayName("${ChatColor.GRAY}VOID ITEM")

                it.itemMeta = itemMeta
                it
            }

            val exitItem = ItemStack(Material.BARRIER).let {
                val itemMeta = it.itemMeta
                itemMeta?.setDisplayName("${ChatColor.RED}Exit")
                itemMeta?.lore = listOf("${ChatColor.WHITE}You can close this gui")

                it.itemMeta = itemMeta
                it
            }

            for (i in 0..44) {
                slot(i, voidItem) {
                    isCancelled = true
                }
            }

            slot(44, exitItem) {
                player.closeInventory()
            }

            for (i in 10..16) {
                slot(i, plants()[i]!!) {
                    when {
                        this.isLeftClick -> {
                            system.sell(plantsItem[i]!!, 1)
                        }
                    }

                    isCancelled = true
                }
            }

            for (i in 19..25) {
                slot(i, resources()[i]!!) {
                    when {
                        this.isLeftClick -> {
                            system.sell(resourcesItem[i]!!, 1)
                        }
                    }

                    isCancelled = true
                }
            }

            slot(25, resourcesItem[25]!!) {
                if (isLeftClick) {
                    system.sell(resourcesItem[25]!!, 1)
                } else if (isRightClick) {
                    system.buy(resourcesItem[25]!!, 1)
                }

                isCancelled = true
            }
        }
    }

    private fun plants(): Map<Int, ItemStack> {
        val wheat      = ItemStack(Material.WHEAT).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineWheat}${moneyUnit()}")
            itemStack
        }

        val potato     = ItemStack(Material.POTATO).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlinePotato}${moneyUnit()}")
            itemStack
        }

        val carrot     = ItemStack(Material.CARROT).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineCarrot}${moneyUnit()}")
            itemStack
        }

        val sugarCane  = ItemStack(Material.SUGAR_CANE).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineSugarCane}${moneyUnit()}")
            itemStack
        }

        val pumpkin    = ItemStack(Material.PUMPKIN).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlinePumpkin}${moneyUnit()}")
            itemStack
        }

        val melonSlice = ItemStack(Material.MELON_SLICE).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineMelonSlice}${moneyUnit()}")
            itemStack
        }

        val cake       = ItemStack(Material.CAKE).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineCake}${moneyUnit()}")
            itemStack
        }

        return mapOf(
            10 to wheat,
            11 to potato,
            12 to carrot,
            13 to sugarCane,
            14 to pumpkin,
            15 to melonSlice,
            16 to cake
        )
    }

    private fun resources(): Map<Int, ItemStack> {
        val cobblestone    = ItemStack(Material.COBBLESTONE).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineCobbleStone}${moneyUnit()}")
            itemStack
        }

        val copperIngot    = ItemStack(Material.COPPER_INGOT).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineCopperIngot}${moneyUnit()}")
            itemStack
        }

        val ironIngot      = ItemStack(Material.IRON_INGOT).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineIronIngot}${moneyUnit()}")
            itemStack
        }

        val goldIngot      = ItemStack(Material.GOLD_INGOT).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineGoldIngot}${moneyUnit()}")
            itemStack
        }

        val diamond        = ItemStack(Material.DIAMOND).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineDiamond}${moneyUnit()}")
            itemStack
        }

        val netheriteIngot = ItemStack(Material.NETHERITE_INGOT).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineNetheriteIngot}${moneyUnit()}")
            itemStack
        }

        val emerald        = ItemStack(Material.EMERALD).let { itemStack ->
            val itemMeta = itemStack.itemMeta
            itemMeta?.lore = listOf("Cost: ${inlineEmerald}${moneyUnit()}")
            itemStack
        }

        return mapOf(
            19 to cobblestone,
            20 to copperIngot,
            21 to ironIngot,
            22 to goldIngot,
            23 to diamond,
            24 to netheriteIngot,
            25 to emerald
        )
    }

    companion object {
        private var wheatCost: Int?      = null
        private var potatoCost: Int?     = null
        private var carrotCost: Int?     = null
        private var sugarCaneCost: Int?  = null
        private var pumpkinCost: Int?    = null
        private var melonSliceCost: Int? = null
        private var cake: Int?           = null

        private var cobblestone: Int?    = null
        private var copperIngot: Int?    = null
        private var ironIngot: Int?      = null
        private var goldIngot: Int?      = null
        private var diamond: Int?        = null
        private var netheriteIngot: Int? = null
        private var emerald: Int?        = null
    }

    private val inlineWheat          = plugin.config.getInt("wheat_cost")
    private val inlinePotato         = plugin.config.getInt("potato_cost")
    private val inlineCarrot         = plugin.config.getInt("carrot_cost")
    private val inlineSugarCane      = plugin.config.getInt("sugarcane_cost")
    private val inlinePumpkin        = plugin.config.getInt("pumpkin_cost")
    private val inlineMelonSlice     = plugin.config.getInt("melon_slice_cost")
    private val inlineCake           = plugin.config.getInt("cake_cost")

    private val inlineCobbleStone    = plugin.config.getInt("cobblestone_cost")
    private val inlineCopperIngot    = plugin.config.getInt("copper_ingot_cost")
    private val inlineIronIngot      = plugin.config.getInt("iron_ingot_cost")
    private val inlineGoldIngot      = plugin.config.getInt("gold_ingot_cost")
    private val inlineDiamond        = plugin.config.getInt("diamond_cost")
    private val inlineNetheriteIngot = plugin.config.getInt("netherite_ingot_cost")
    private val inlineEmerald        = plugin.config.getInt("emerald_cost")

    init {
        wheatCost      = inlineWheat
        potatoCost     = inlinePotato
        carrotCost     = inlineCarrot
        sugarCaneCost  = inlineSugarCane
        pumpkinCost    = inlinePumpkin
        melonSliceCost = inlineMelonSlice
        cake           = inlineCake

        cobblestone    = inlineCobbleStone
        copperIngot    = inlineCopperIngot
        ironIngot      = inlineIronIngot
        goldIngot      = inlineGoldIngot
        diamond        = inlineDiamond
        netheriteIngot = inlineNetheriteIngot
        emerald        = inlineEmerald
    }
}