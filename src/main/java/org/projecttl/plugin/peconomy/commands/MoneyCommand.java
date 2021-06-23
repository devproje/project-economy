package org.projecttl.plugin.peconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.projecttl.plugin.peconomy.PEconomy;
import org.projecttl.plugin.peconomy.utils.Economy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoneyCommand implements CommandExecutor, TabCompleter {

    private final PEconomy plugin;
    public MoneyCommand(PEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Economy system = new Economy(plugin, player);

            if (command.getName().equals("peconomy")) {
                switch (args.length) {
                    case 0:
                        player.sendMessage("<PEconomy> " + ChatColor.GREEN + "You account balance is $" + system.getMoney());
                        return true;

                    case 3:
                        if (!player.isOp()) {
                            if (args[0].equals("send")) {
                                String targetName = args[1];
                                Player target = Bukkit.getPlayer(targetName);

                                int amount = Integer.parseInt(args[2]);
                                int result = system.getMoney() - amount;

                                if (!Objects.requireNonNull(target).isOnline()) {
                                    player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                } else {
                                    if (result <= 0) {
                                        player.sendMessage(ChatColor.GOLD + "Transaction Failed!");
                                    } else {
                                        int getTargetAmount = system.getPlayerMoney(target);
                                        system.removeMoney(amount);
                                        system.setPlayerMoney(target, getTargetAmount + amount);
                                    }
                                }

                                return true;
                            }
                        } else {
                            String targetPlayer = args[1];
                            int amount = Integer.parseInt(args[2]);

                            Player target = Bukkit.getPlayer(targetPlayer);

                            switch (args[0]) {
                                case "add":
                                    if (!Objects.requireNonNull(target).isOnline()) {
                                        player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                    } else {
                                        int money = system.getPlayerMoney(target);
                                        system.setPlayerMoney(target, money + amount);
                                    }

                                    return true;

                                case "remove":
                                    if (!Objects.requireNonNull(target).isOnline()) {
                                        player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                    } else {
                                        int money = system.getPlayerMoney(target);
                                        system.setPlayerMoney(target, money - amount);
                                    }

                                    return true;

                                case "set":
                                    if (!Objects.requireNonNull(target).isOnline()) {
                                        player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                    } else {
                                        system.setPlayerMoney(target, amount);
                                    }

                                    return true;
                            }
                        }
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        ArrayList<String> commandList = new ArrayList<>();
        Economy system = new Economy(plugin, ((Player) sender));

        if (command.getName().equals("peconomy")) {
            switch (args.length) {
                case 1:
                    if (sender.isOp()) {
                        commandList.add("add");
                        commandList.add("remove");
                        commandList.add("set");
                    }

                    commandList.add("send");
                    return commandList;

                case 2:
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        commandList.add(player.getName());
                    }

                    return commandList;

                case 3:
                    if (sender instanceof Player) {
                        if (args[0].equals("send")) {
                            commandList.add("" + system.getMoney());

                            return commandList;
                        }
                    }
            }
        }

        return null;
    }
}