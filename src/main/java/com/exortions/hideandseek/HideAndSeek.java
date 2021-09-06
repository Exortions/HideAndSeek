package com.exortions.hideandseek;

import com.exortions.hideandseek.handlers.CommandHandler;
import com.exortions.hideandseek.handlers.TabCompleteHandler;
import com.exortions.hideandseek.util.database.DatabaseHandler;
import com.exortions.pluginutils.chat.ChatUtils;
import com.exortions.pluginutils.command.subcommand.SubCommandHandler;
import com.exortions.pluginutils.config.Configuration;
import com.exortions.pluginutils.plugin.JavaVersion;
import com.exortions.pluginutils.plugin.MinecraftVersion;
import com.exortions.pluginutils.plugin.SpigotPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public final class HideAndSeek extends SpigotPlugin {

    public static String PREFIX = "";

    @Getter
    private static HideAndSeek plugin;

    @Getter @Setter
    private static SubCommandHandler commandHandler;
    @Getter @Setter
    private static Configuration configuration;
    @Getter @Setter
    private static DatabaseHandler database;

    @Getter
    private static HashMap<String, String> messages;

    private int loadTime;

    @Override
    public void onEnable() {
        loadTime = 0;
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                loadTime++;
            }
        }.runTaskTimer(this, 0L, 1L);

        saveDefaultConfig();

        plugin = this;

        PREFIX = ChatUtils.colorize(new Configuration(HideAndSeek.getPlugin(), "config.yml").getString("messages.prefix"));
        sendStartupMessage();

        registerListeners();
        registerCommands();

        loadConfiguration();
        loadMessages();
        loadStorage();
        loadPermissions();
        loadData();

        task.cancel();
        loadTime = loadTime*200;
        sendMessage(PREFIX + ChatColor.WHITE + "Successfully enabled Hide and Seek v" + getPluginVersion() + " in " + loadTime + " ms.");
        loadTime = 0;
    }

    @Override
    public void registerListeners() {
        sendMessage(PREFIX + ChatColor.WHITE + "Registering listeners...");
    }

    @Override
    public void registerCommands() {
        sendMessage(PREFIX + ChatColor.WHITE + "Registering commands...");
        PluginCommand command = getCommand("hideandseek");
        if (command == null) {
            sendMessage(PREFIX + ChatColor.RED + "Could not get main command /hideandseek registered with the server!");
            return;
        }
        commandHandler = new CommandHandler();
        command.setExecutor(commandHandler);
        command.setTabCompleter(new TabCompleteHandler());
    }

    private void loadConfiguration() {
        sendMessage(PREFIX + ChatColor.WHITE + "Loading configuration...");
        saveDefaultConfig();

        configuration = new Configuration(this, "config.yml");
        DatabaseHandler.setDatabaseType(configuration.getString("storage.storage-type"));
    }

    private void loadMessages() {
        sendMessage(PREFIX + ChatColor.WHITE + "Loading messages...");
        messages = new HashMap<>();
        messages.put("onlyPlayers", ChatUtils.colorize(configuration.getString("messages.commands.only-players")));
        messages.put("noPermission", ChatUtils.colorize(configuration.getString("messages.commands.player-no-permission")));
        messages.put("unknownCommand", ChatUtils.colorize(configuration.getString("messages.commands.player-unknown-command")));
    }

    private void loadStorage() {
        sendMessage(PREFIX + ChatColor.WHITE + "Loading storage provider...");
        if (DatabaseHandler.getDatabaseType().equalsIgnoreCase("mysql")) {
            sendMessage(PREFIX + ChatColor.WHITE + "Using database storage type \"MySQL\"...");
            database = new DatabaseHandler(configuration.getString("storage.database.database"), configuration.getString("storage.database.host"), configuration.getString("storage.database.port"), configuration.getString("storage.database.username"), configuration.getString("storage.database.password"));
            sendMessage(PREFIX + ChatColor.WHITE + "Creating database connection...");
            database.createConnection();
            if (database.getConnection() == null) {
                sendMessage(PREFIX + ChatColor.RED + "Failed to create connection to database! Disabling plugin...");
                Bukkit.getPluginManager().disablePlugin(this);
            } else sendMessage(PREFIX + ChatColor.WHITE + "Successfully created connection!");
            database.createLocationsTable();
        } else {
            sendMessage(ChatColor.RED + "FATAL ERROR: Could not parse \"storage.storage-type\" in config.yml! Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void loadPermissions() {
        sendMessage(PREFIX + ChatColor.WHITE + "Loading permission managers...");
    }

    private void loadData() {
        sendMessage(PREFIX + ChatColor.WHITE + "Performing initial data load...");
    }

    private void sendStartupMessage() {
        sendMessage(" ");
        sendMessage(ChatColor.RED + "   |__| " + ChatColor.GOLD +"|__|   " + ChatColor.RESET + "HideAndSeek v" + getPluginVersion());
        sendMessage(ChatColor.RED + "   |  | " + ChatColor.GOLD + "|  |    " + ChatColor.RESET + "Running on Bukkit - CraftBukkit");
        sendMessage(" ");
    }

    @Override
    public void onDisable() {
        sendMessage(PREFIX + ChatColor.GRAY + "Successfully disabled Hide and Seek v" + getPluginVersion() + ".");
    }

    public void reload() {
        setConfiguration(new Configuration(HideAndSeek.getPlugin(), "config.yml"));
    }

    @Override
    public String getPluginName() {
        return "HideAndSeek";
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override
    public MinecraftVersion getPluginMinecraftVersion() {
        return MinecraftVersion.MINECRAFT_1_16_5;
    }

    @Override
    public JavaVersion getJavaVersion() {
        return JavaVersion.JAVA_1_1;
    }
}
