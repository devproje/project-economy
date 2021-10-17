package net.projecttl.economy.plugin.listeners;

import net.projecttl.economy.Economy;
import net.projecttl.economy.EconomyKt;
import net.projecttl.economy.plugin.ProjectEconomy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record RegisterListener(ProjectEconomy instance) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        instance.connect();
        Player player = event.getPlayer();
        Economy economy = new Economy(player);
        economy.registerAccount();

        player.sendMessage(
            ChatColor.GOLD + "================================" +
            ChatColor.WHITE + "Hello, " + player.getName() + "!" +
            "Your balance is" + economy.getMoney() + EconomyKt.moneyUnit() +
            "Good luck!" +
            ChatColor.GOLD + "================================"
        );

        instance.disconnect();
    }
}
