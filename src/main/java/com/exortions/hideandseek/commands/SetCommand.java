package com.exortions.hideandseek.commands;

import com.exortions.hideandseek.HideAndSeek;
import com.exortions.hideandseek.util.database.DatabaseHandler;
import com.exortions.pluginutils.command.subcommand.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
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
            String PREFIX = HideAndSeek.PREFIX;
            Location loc;
            switch (args[0]) {
                case "lobby":
                    if (args.length == 1) loc = player.getLocation();
                    else
                        loc = new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), player.getLocation().getYaw(), player.getLocation().getPitch());
                    setLobby(loc);
                    player.sendMessage(PREFIX + ChatColor.WHITE + "Successfully set lobby to: " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                    break;
                case "hiderspawn":
                    if (args.length == 1) loc = player.getLocation();
                    else
                        loc = new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), player.getLocation().getYaw(), player.getLocation().getPitch());
                    setHiderSpawn(loc);
                    player.sendMessage(PREFIX + ChatColor.WHITE + "Successfully set hider spawn to: " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                    break;
                case "seekerspawn":
                    if (args.length == 1) loc = player.getLocation();
                    else
                        loc = new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), player.getLocation().getYaw(), player.getLocation().getPitch());
                    setSeekerSpawn(loc);
                    player.sendMessage(PREFIX + ChatColor.WHITE + "Successfully set seeker spawn to: " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Incorrect usage!");
                    for (String usage : usage().split(" OR "))
                        player.sendMessage(ChatColor.RED + " > " + ChatColor.WHITE + usage);
                    break;
            }
        }
    }

    private void setLobby(Location location) {
        HideAndSeek.getDatabase().addLocation(DatabaseHandler.LocationType.LOBBY, "" + Math.floor(location.getX()), "" + Math.floor(location.getY()), "" + Math.floor(location.getZ()));
    }

    private void setHiderSpawn(Location location) {
        HideAndSeek.getDatabase().addLocation(DatabaseHandler.LocationType.HIDER_SPAWN, "" + Math.floor(location.getX()), "" + Math.floor(location.getY()), "" + Math.floor(location.getZ()));
    }

    private void setSeekerSpawn(Location location) {
        HideAndSeek.getDatabase().addLocation(DatabaseHandler.LocationType.SEEKER_SPAWN, "" + Math.floor(location.getX()), "" + Math.floor(location.getY()), "" + Math.floor(location.getZ()));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}