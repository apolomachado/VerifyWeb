package nescaaallz.verifyweb.database;

import nescaaallz.verifyweb.VerifyWeb;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProvider {

    protected final VerifyWeb plugin;
    protected Connection connection;
    protected List<String> cache = new ArrayList<>();
    public String table;

    public DatabaseProvider(VerifyWeb plugin) {
        this.plugin = plugin;
    }

    public VerifyWeb getPlugin() {
        return plugin;
    }

    public FileConfiguration getConfig() {
        return getPlugin().getConfig();
    }

    private Connection getConnection() {
        return connection;
    }

    public List<String> getCache() {
        return cache;
    }

    public void startConnection() {

        String address, database, url, user, password;
        address = getConfig().getString("MySQL.Address");
        database = getConfig().getString("MySQL.Database");
        url = "jdbc:mysql://" + address + "/" + database + "?autoReconnect=true";
        user = getConfig().getString("MySQL.Username");
        password = getConfig().getString("MySQL.Password");
        table = getConfig().getString("MySQL.Table");

        try {
            this.connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + table + "` ( `id` INT PRIMARY KEY AUTO_INCREMENT , `player_name` VARCHAR(16) NOT NULL , `registered_date` TEXT NOT NULL , `ip` VARCHAR(50) NOT NULL );");
            preparedStatement.execute();
            preparedStatement.close();

            grab();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean executeExistsSearch(Player player) {
        boolean result = false;
        if(!getCache().contains(player.getName().toLowerCase())) {
            try {

                PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `player_name` = ?;");
                preparedStatement.setString(1, player.getName().toLowerCase());
                ResultSet resultSet = preparedStatement.executeQuery();

                result = resultSet.next();
                resultSet.close();
                preparedStatement.close();

                if(result)
                    getCache().add(player.getName().toLowerCase());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            result = true;
        }
        return result;
    }

    public void grab() {
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM `" + table + "`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                getCache().add(resultSet.getString("player_name"));
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        if(getConnection() != null) {
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}