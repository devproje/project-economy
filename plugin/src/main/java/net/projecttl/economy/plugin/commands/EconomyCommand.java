package net.projecttl.economy.plugin.commands;

import net.kyori.adventure.text.Component;
import net.projecttl.economy.Economy;
import net.projecttl.economy.plugin.ProjectEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.projecttl.economy.EconomyKt.moneyUnit;

public record EconomyCommand(ProjectEconomy instance) implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if (Objects.requireNonNull(command).getName().equals("money")) {
            if (sender instanceof Player player) {
                Economy economy = new Economy(player);
                instance.connect();

                if (args.length == 0) {
                    player.sendMessage("Your account balance: " + ChatColor.GREEN + economy.getMoney() + moneyUnit());
                    return false;
                } else {
                    if (args[0].equalsIgnoreCase("send")) {
                        String name = args[0];
                        int amount  = Integer.parseInt(args[1]);

                        Player target = Bukkit.getPlayer(name);
                        Economy targetAccount = new Economy(Objects.requireNonNull(target));

                        if (!target.isOnline()) {
                            player.sendMessage(ChatColor.GOLD + target.getName() + " is offline!");
                        } else {
                            if (economy.getMoney() < amount) {
                                player.sendMessage(ChatColor.RED + "You cannot send more than the amount you have.");
                            } else if (economy.getMoney() <= 0 || amount <= 0) {
                                player.sendMessage(ChatColor.RED + "Balance must not be less of 0" + moneyUnit());
                            } else {
                                economy.removeMoney(amount);
                                targetAccount.addMoney(amount);

                                player.sendMessage("You have successfully sent " + amount + moneyUnit() + " to" + target.getName());
                                target.sendMessage("You received " + amount + moneyUnit() + " from " + player.getName());
                            }
                        }

                        return true;
                    } else if (args[0].equalsIgnoreCase("rank")) {
                        economy.getRanking();
                        return true;
                    } else if (args[0].equalsIgnoreCase("account")) {
                        instance.connect();

                        sender.sendMessage(ChatColor.GOLD +
                                "==========" + "[" + ChatColor.RESET + player.getName() + "'s account" + ChatColor.GOLD + "]=========="
                        );
                        sender.sendMessage(ChatColor.YELLOW + "UUID" + ChatColor.RESET + ": " + player.getUniqueId());
                        sender.sendMessage(ChatColor.YELLOW + "Balance" + ChatColor.RESET + ": " + economy.getMoney() + moneyUnit());

                        return true;
                    } else if (args[0].equalsIgnoreCase("add")) {
                        instance.connect();
                        if (player.isOp()) {
                            String name = args[1];
                            int amount = Integer.parseInt(args[2]);

                            Player target = Bukkit.getPlayer(name);
                            Economy targetAccount = new Economy(Objects.requireNonNull(target));

                            if (amount >= 0) {
                                if (!target.isOnline()) {
                                    sender.sendMessage(ChatColor.GOLD + target.getName() + " is offline!");
                                } else {
                                    targetAccount.addMoney(amount);
                                    sender.sendMessage(ChatColor.GREEN + "Now " + target.getName() + "'s account balance is " + amount + moneyUnit());
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Balance must not be less of 0" + moneyUnit());
                            }
                        }

                        return true;
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        instance.connect();
                        if (player.isOp()) {
                            String name = args[1];
                            int amount = Integer.parseInt(args[2]);

                            if (amount >= 0) {
                                Player target = Bukkit.getPlayer(name);
                                Economy targetAccount = new Economy(Objects.requireNonNull(target));

                                if (!target.isOnline()) {
                                    sender.sendMessage(ChatColor.GOLD + target.getName() + " is offline!");
                                } else {
                                    targetAccount.removeMoney(amount);
                                    sender.sendMessage(ChatColor.GREEN + "Now " + target.getName() + "'s account balance is " + amount + moneyUnit());
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Balance must not be less of 0" + moneyUnit());
                            }
                        }

                        return true;
                    } else if (args[0].equalsIgnoreCase("set")) {
                        instance.connect();
                        if (sender.isOp()) {
                            String name = args[1];
                            int amount = Integer.parseInt(args[2]);

                            if (amount >= 0) {
                                Player target = Bukkit.getPlayer(name);
                                Economy targetAccount = new Economy(Objects.requireNonNull(target));

                                if (!target.isOnline()) {
                                    sender.sendMessage(ChatColor.GOLD + target.getName() + " is offline!");
                                } else {
                                    targetAccount.setMoney(amount);
                                    sender.sendMessage(ChatColor.GREEN + "Now " + target.getName() + "'s account balance is " + amount + moneyUnit());
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Balance must not be less of 0" + moneyUnit());
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You're not OP!");
                        }

                        return true;
                    } else if (args[0].equalsIgnoreCase("query")) {
                        instance.connect();
                        if (player.isOp()) {
                            economy.queryList();
                        } else {
                            sender.sendMessage(ChatColor.RED + "You're not OP!");
                        }

                        return true;
                    } else if (args[0].equalsIgnoreCase("moneyunit")) {
                        instance.connect();
                        String unit = args[1];
                        if (sender.isOp()) {
                            if (unit.equals("")) {
                                sender.sendMessage(ChatColor.RED + "Money Unit must not be null!");
                            } else {
                                instance.getConfig().set("MONEY_UNIT", unit);
                                Bukkit.broadcast(
                                        Component.text(
                                                ChatColor.GREEN +
                                                        "Now your server money unit is this: " +
                                                        ChatColor.WHITE + unit
                                        )
                                );
                                Bukkit.broadcast(
                                        Component.text(
                                                 ChatColor.GOLD +
                                                         "If you wanna change money unit, please type reload command or restart server."
                                        )
                                );
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You're not OP!");
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> arr = new ArrayList<>();
        if (args.length == 1) {
            arr.add("account");
            arr.add("add");
            arr.add("moneyunit");
            arr.add("query");
            arr.add("rank");
            arr.add("remove");
            arr.add("send");
            arr.add("set");

            return arr;
        }

        return null;
    }
}
