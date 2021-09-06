package com.exortions.hideandseek.util.database;

import com.exortions.hideandseek.HideAndSeek;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.sql.*;

@Data
@SuppressWarnings("UnusedReturnValue")
public class DatabaseHandler {

    @Getter
    @Setter
    private static String databaseType;

    private String database;
    private String host;
    private String port;

    private String username;
    private String password;

    private Connection connection;
    private Statement statement;

    public DatabaseHandler(String database, String host, String port, String username, String password) {
        this.database = database;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public DatabaseHandler createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void createLocationsTable() {
        HideAndSeek.getPlugin().sendMessage(HideAndSeek.PREFIX + ChatColor.WHITE + "Creating table 'locations'...");
        String sql = "CREATE TABLE IF NOT EXISTS `" + database + "`.`locations` (" +
                "  `name` VARCHAR(100) NOT NULL," +
                "  `x` VARCHAR(45) NOT NULL," +
                "  `y` VARCHAR(45) NOT NULL," +
                "  `z` VARCHAR(45) NOT NULL," +
                "  PRIMARY KEY (`name`));";
        executeAction(sql);

        HideAndSeek.getPlugin().sendMessage(HideAndSeek.PREFIX + ChatColor.WHITE + "Inserting lobby,hiderspawn,seekerspawn into `" + database + "`.`locations`!");
        ResultSet set = executeQuery("SELECT * FROM locations");
        try {
            if (!set.next()) {
                String lobby = "INSERT INTO `hideandseek`.`locations`" +
                        "(`name`," +
                        "`x`," +
                        "`y`," +
                        "`z`)" +
                        "VALUES" +
                        "('lobby'," +
                        "'0'," +
                        "'0'," +
                        "'0');";
                String hiderSpawn = "INSERT INTO `hideandseek`.`locations`" +
                        "(`name`," +
                        "`x`," +
                        "`y`," +
                        "`z`)" +
                        "VALUES" +
                        "('hiderspawn'," +
                        "'0'," +
                        "'0'," +
                        "'0');";
                String seekerSpawn = "INSERT INTO `hideandseek`.`locations`" +
                        "(`name`," +
                        "`x`," +
                        "`y`," +
                        "`z`)" +
                        "VALUES" +
                        "('seekerspawn'," +
                        "'0'," +
                        "'0'," +
                        "'0');";
                executeAction(lobby);
                executeAction(hiderSpawn);
                executeAction(seekerSpawn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addLocation(LocationType type, String x, String y, String z) {
        String sql = "UPDATE `" + database + "`.`locations`" +
                "SET" +
                "`name` = '" + type.getName() + "'," +
                "`x` = '" + x + "'," +
                "`y` = '" + y + "'," +
                "`z` = '" + z + "'" +
                "WHERE `name` = '" + type.getName() + "';";
        executeAction(sql);
    }

    public enum LocationType {
        LOBBY("lobby"),
        HIDER_SPAWN("hiderspawn"),
        SEEKER_SPAWN("seekerspawn"),
        ;

        private final String name;

        LocationType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean executeAction(String sql) {
        try {
            statement = connection.createStatement();
            return statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

