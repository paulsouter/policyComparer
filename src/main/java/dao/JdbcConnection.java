package dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

public class JdbcConnection {

    private static final String USERNAME = "sa"; // H2 default
    private static final String PASSWORD = "";   // H2 default

    private static JdbcConnectionPool pool;

    public static Connection getConnection(String url) {

        if (pool == null) {
            pool = JdbcConnectionPool.create(url, USERNAME, PASSWORD);
        }

        try {
            return pool.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}