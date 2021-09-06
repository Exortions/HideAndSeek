package com.exortions.hideandseek.commands;

import com.exortions.hideandseek.HideAndSeek;
import com.exortions.pluginutils.command.subcommand.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class ReloadCommand implements SubCommand {
    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String permission() {
        return "hideandseek.reload";
    }

    @Override
    public String usage() {
        return "/hideandseek reload";
    }

    @Override
    public String description() {
        return "Reloads all configurations for Hide and Seek.";
    }

    @Override
    public List<String> tabcompletion() {
        return null;
    }

    @Override
    public boolean requiresPlayer() {
        return false;
    }

    @Override
    public void execute(Player player, String[] args) {

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final int[] ms = {0};
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                ms[0]++;
            }
        }.runTaskTimer(HideAndSeek.getPlugin(), 0L, 1L);

        HideAndSeek.getPlugin().reload();

        task.cancel();
        ms[0] = ms[0] *200;
        sender.sendMessage(HideAndSeek.PREFIX + "Successfully reloaded HideAndSeek in " + ms[0] + " ms.");
    }
}
