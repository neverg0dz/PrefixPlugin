package dev.nevergodz.prefixplugin.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.nevergodz.prefixplugin.util.UuidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {
    private final HikariDataSource dataSource;

    public DatabaseManager(String jdbcUrl, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        dataSource = new HikariDataSource(hikariConfig);

        createTables();
    }


    private void createTables() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS prefixes (" +
                             "uuid BINARY(16) PRIMARY KEY, " +
                             "prefix_id VARCHAR(255)" +
                             ")"
             )) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPrefix(UUID uuid, String prefixId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO prefixes (uuid, prefix_id) VALUES (?, ?) " +
                             "ON DUPLICATE KEY UPDATE prefix_id = VALUES(prefix_id)"
             )) {
            statement.setBytes(1, UuidUtil.uuidToBytes(uuid));
            statement.setString(2, prefixId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Failed to set prefix for UUID: " + uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPrefix(UUID uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT prefix_id FROM prefixes WHERE uuid = ?"
             )) {
            statement.setBytes(1, UuidUtil.uuidToBytes(uuid));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("prefix_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
