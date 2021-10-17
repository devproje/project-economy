package net.projecttl.economy.plugin.commands;

import net.md_5.bungee.api.ChatColor;
import net.projecttl.economy.Economy;
import net.projecttl.economy.EconomyKt;
import net.projecttl.economy.plugin.ProjectEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record EconomyCommand(ProjectEconomy instance) implements CommandExecutor {

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if (Objects.requireNonNull(command).getName().equals("money")) {
            if (sender instanceof Player player) {
                Economy economy = new Economy(player);

                if (args.length == 0) {
                    player.sendMessage("Your account balance: " + ChatColor.GREEN + economy.getMoney() + EconomyKt.moneyUnit());
                    return false;
                }
            }
        }

        return false;
    }
}
