package org.projecttl.plugin.peconomy.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.projecttl.plugin.peconomy.PEconomy;

@Deprecated
public class EconomySystem {

    private final PEconomy plugin;
    public EconomySystem(PEconomy plugin) {
        this.plugin = plugin;
    }

    public int getMoney(Player player) {
        return plugin.pEconomyConfig().getInt("peconomy.account." + player.getName() + ".amount");
    }

    public void setMoney(Player player, int amount) {
        plugin.pEconomyConfig().set("peconomy.account." + player.getName() + ".amount", amount);
        player.sendMessage("<PEconomy> " + ChatColor.GREEN + "The money in your account has been set to $" + "amount.");
    }

    public void addMoney(Player player, int amount) {
        plugin.pEconomyConfig().set("peconomy.account." + player.getName() +".amount", getMoney(player) + amount);
        player.sendMessage("<PEconomy> "+ ChatColor.GREEN + "$" + amount + " has been added to the your account");
    }

    public boolean removeMoney(Player player, int amount) {
        player.sendMessage(ChatColor.GOLD + "Transaction...");

        int value = getMoney(player) - amount;

        if (value <= 0) {
            player.sendMessage("<PEconomy> " + ChatColor.RED + "Not enough balance.");
            return false;
        } else {
            plugin.pEconomyConfig().set("peconomy.account." + player.getName() + ".amount", getMoney(player) - amount);
            player.sendMessage("<PEconomy> " + ChatColor.GREEN + "$" + amount + " has been removed to the your account");

            return true;
        }
    }

    public void sendMoney(Player player, Player target, int amount) {
        removeMoney(player, amount);

        if (amount <= getMoney(player)) {
            player.sendMessage(ChatColor.GOLD + "Transaction Failed!");
        } else {
            addMoney(target, amount);
        }
    }
}
