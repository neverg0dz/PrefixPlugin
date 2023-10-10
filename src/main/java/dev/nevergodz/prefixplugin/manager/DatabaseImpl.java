package dev.nevergodz.prefixplugin.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.nevergodz.prefixplugin.data.PlayerData;

import java.sql.*;

public class DatabaseImpl {
    String tableName = "neverdb";
    private final HikariDataSource src;
    public DatabaseImpl(String host, int port, String database, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepSt" +
                "mtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        src = new HikariDataSource(config);

        try(Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `" + String.format(tableName) + "` (" +
                            "`player_uuid` BINARY(16) PRIMARY KEY, " +
                            "`inventory` VARCHAR(2048), " +
                            "`armor` VARCHAR(128))”"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        return src.getConnection();
    }


    public PlayerData getPlayerData(byte[] uuid) {
        String query = "SELECT * FROM `" + String.format(tableName) + "` WHERE `player_uuid` = ?";
        PlayerData playerData = null;
        try(Connection c = connect(); PreparedStatement statement = c.prepareStatement(query)) {
            statement.setBytes(1, uuid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getBytes("player_uuid") == uuid) {
                    //
                }
            }
            rs.close();
            return playerData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerData;
    }

    public void close() {
        src.close();
    }
}
