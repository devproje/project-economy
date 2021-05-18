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
import org.projecttl.plugin.peconomy.utils.EconomySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record MoneyCommand(PEconomy plugin) implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            EconomySystem system = new EconomySystem(plugin);

            if (command.getName().equals("peconomy")) {
                switch (args.length) {
                    case 0 -> {
                        player.sendMessage("<PEconomy> " + ChatColor.GREEN + "You account balance is $" + system.getMoney(player));
                        return true;
                    }

                    case 3 -> {
                        if (!player.isOp()) {
                            if (args[0].equals("send")) {
                                String targetName = args[1];
                                int amount = Integer.parseInt(args[2]);

                                Player target = Bukkit.getPlayer(targetName);

                                if (!Objects.requireNonNull(target).isOnline()) {
                                    player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                } else {
                                    system.sendMoney(player, target, amount);
                                }

                                return true;
                            }
                        } else {
                            switch (args[0]) {
                                case "add" -> {
                                    String targetPlayer = args[1];
                                    int amount = Integer.parseInt(args[2]);

                                    Player target = Bukkit.getPlayer(targetPlayer);

                                    if (!Objects.requireNonNull(target).isOnline()) {
                                        player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                    } else {
                                        system.addMoney(target, amount);
                                    }

                                    return true;
                                }

                                case "remove" -> {
                                    String targetPlayer = args[1];
                                    int amount = Integer.parseInt(args[2]);

                                    Player target = Bukkit.getPlayer(targetPlayer);

                                    if (!Objects.requireNonNull(target).isOnline()) {
                                        player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                    } else {
                                        system.removeMoney(target, amount);
                                    }

                                    return true;
                                }

                                case "set" -> {
                                    String targetPlayer = args[1];
                                    int amount = Integer.parseInt(args[2]);
                                    Player target = Bukkit.getPlayer(targetPlayer);

                                    if (!Objects.requireNonNull(target).isOnline()) {
                                        player.sendMessage("<PEconomy> " + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + " is Offline!");
                                    } else {
                                        system.setMoney(target, amount);
                                    }

                                    return true;
                                }
                            }
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
        EconomySystem system = new EconomySystem(plugin);

        if (command.getName().equals("peconomy")) {
            switch (args.length) {
                case 1 -> {
                    if (sender.isOp()) {
                        commandList.add("add");
                        commandList.add("remove");
                        commandList.add("set");
                    }

                    commandList.add("send");
                    return commandList;
                }

                case 2 -> {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        commandList.add(player.getName());
                    }

                    return commandList;
                }

                case 3 -> {
                    if (sender instanceof Player player) {
                        if (args[0].equals("send")) {
                            commandList.add("" + system.getMoney(player));

                            return commandList;
                        }
                    }
                }
            }
        }

        return null;
    }
}