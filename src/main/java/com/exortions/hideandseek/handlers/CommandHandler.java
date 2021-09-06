package com.exortions.hideandseek.handlers;

import com.exortions.hideandseek.HideAndSeek;
import com.exortions.hideandseek.commands.HelpCommand;
import com.exortions.hideandseek.commands.ReloadCommand;
import com.exortions.hideandseek.commands.SetCommand;
import com.exortions.pluginutils.command.subcommand.SubCommand;
import com.exortions.pluginutils.command.subcommand.SubCommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandHandler extends SubCommandHandler {

    public CommandHandler() {
        super(null, null, null, null);
        String PREFIX = HideAndSeek.PREFIX;
        setOnlyPlayers(() -> getSender().sendMessage(HideAndSeek.getMessages().get("onlyPlayers")));
        setNoArguments(() -> {
            CommandSender sender = getSender();
            getSender().sendMessage(PREFIX + "Running " + ChatColor.RED + "HideAndSeek v" + HideAndSeek.getPlugin().getPluginVersion() + ChatColor.WHITE + ".");
            boolean hasPermissions = false;
            for (SubCommand subcommand : new CommandHandler().getSubcommands()) if (sender.hasPermission(subcommand.permission())) hasPermissions = true;
            if (hasPermissions) getSender().sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + "Type /hs help for more info.");
            else getSender().sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + "You do not have permission to use any sub-commands.");
        });
        setNoPermission(() -> getSender().sendMessage(HideAndSeek.getMessages().get("noPermission")));
        setSubComandNotFound(() -> getSender().sendMessage(HideAndSeek.getMessages().get("unknownCommand")));

        List<SubCommand> subCommands = getSubcommands();
        subCommands.add(new ReloadCommand());
        subCommands.add(new HelpCommand());
        subCommands.add(new SetCommand());

        setSubcommands(subCommands);
    }

}
