package org.projecttl.plugin.peconomy.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.projecttl.plugin.peconomy.PEconomy;

public class Economy {

    private final PEconomy plugin;
    private final Player target;

    private String accountPath = "peconomy.account";

    public Economy(PEconomy instance, Player player) {
        this.plugin = instance;
        this.target = player;
    }

    public int getMoney() {
        return plugin.pEconomyConfig().getInt(accountPath + target.getName() + ".amount");
    }

    public void setMoney(int amount) {
        plugin.pEconomyConfig().set(accountPath + target.getName() + ".amount", amount);
    }

    public void addMoney(int amount) {
        int getAmount = getMoney();
        target.sendMessage("<PEconomy> "+ ChatColor.GREEN + "$" + amount + " has been added to the your account");
        setMoney(getAmount + amount);
    }

    public boolean removeMoney(int amount) {
        int getAmount = getMoney();
        target.sendMessage(ChatColor.GOLD + "Transaction...");

        if (getAmount <= 0) {
            target.sendMessage("<PEconomy> " + ChatColor.RED + "Not enough balance.");
            return false;
        } else {
            setMoney(getAmount - amount);
            target.sendMessage("<PEconomy> " + ChatColor.GREEN + "$" + amount + " has been removed to the your account");

            return true;
        }
    }

    public int getPlayerMoney(Player player) {
        return plugin.pEconomyConfig().getInt(accountPath + player.getName() + ".amount");
    }

    public void setPlayerMoney(Player player, int amount) {
        plugin.pEconomyConfig().set(accountPath + player.getName() + ".amount", amount);
    }
}
