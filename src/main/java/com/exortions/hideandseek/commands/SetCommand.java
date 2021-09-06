package com.exortions.hideandseek.commands;

import com.exortions.pluginutils.command.subcommand.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetCommand implements SubCommand {
    @Override
    public String name() {
        return "set";
    }

    @Override
    public String permission() {
        return "hideandseek.set";
    }

    @Override
    public String usage() {
        return "/hideandseek set <lobby|hiderspawn|seekerspawn> OR /hideandseek set <lobby|hiderspawn|seekerspawn> <coordinates>";
    }

    @Override
    public String description() {
        return "Sets a specific game location like the Lobby for Hide and Seek to a specific coordinate.";
    }

    @Override
    public List<String> tabcompletion() {
        return null;
    }

    @Override
    public boolean requiresPlayer() {
        return true;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Incorrect usage!");
            for (String usage : usage().split(" OR ")) player.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + usage);
        } else {
            if (args[0].equals("lobby")) {
                if (args.length == 1) setLobby(player.getLocation(), player); else setLobby(new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), player.getLocation().getYaw(), player.getLocation().getPitch()), player);
            } else if (args[0].equals("hiderspawn")) {
                if (args.length == 1) setHiderSpawn(player.getLocation(), player); else setHiderSpawn(new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), player.getLocation().getYaw(), player.getLocation().getPitch()), player);
            } else if (args[0].equals("seekerspawn")) {
                if (args.length == 1) setSeekerSpawn(player.getLocation(), player); else setSeekerSpawn(new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), player.getLocation().getYaw(), player.getLocation().getPitch()), player);
            } else {
                player.sendMessage(ChatColor.RED + "Incorrect usage!");
                for (String usage : usage().split(" OR ")) player.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + usage);
            }
        }
    }

    private void setLobby(Location location, Player player) {
        // TODO: Set the lobby
        player.sendMessage("" + location.getX());
        player.sendMessage("" + location.getY());
        player.sendMessage("" + location.getZ());
    }

    private void setHiderSpawn(Location location, Player player) {
        // TODO: Set the hider's spawn
    }

    private void setSeekerSpawn(Location location, Player player) {
        // TODO: Set the seeker's spawn
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}