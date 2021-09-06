package com.exortions.hideandseek.handlers;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.pluginutils.command.tabcomplete.TabCompleterHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteHandler implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        List<SubCommand> subcommands = new CommandHandler().getSubcommands();
        List<String> tab = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommand subCommand : subcommands) tab.add(subCommand.name());
        } else if (args.length == 2) {
            switch (args[0]) {
                case "help":
                    for (SubCommand subCommand : subcommands) tab.add(subCommand.name());
                    break;
                case "reload":
                    tab.add("");
                    break;
                case "set":
                    tab.add("lobby");
                    tab.add("hiderspawn");
                    tab.add("seekerspawn");
                    break;
            }
        } else if (args.length == 3) {
            if (args[0].equals("set")) {
                Player player = (Player) sender;
                tab.add("" + Math.floor(player.getLocation().getX()) + " " + Math.floor(player.getLocation().getY()) + " " + Math.floor(player.getLocation().getZ()));
            }
        } else if (args.length == 4) {
            if (args[0].equals("set")) {
                Player player = (Player) sender;
                tab.add("" + Math.floor(player.getLocation().getY()) + " " + Math.floor(player.getLocation().getZ()));
            }
        } else if (args.length == 5) {
            if (args[0].equals("set")) {
                Player player = (Player) sender;
                tab.add("" + Math.floor(player.getLocation().getZ()));
            }
        }
        return tab;
    }
}
