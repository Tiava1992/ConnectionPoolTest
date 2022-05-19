package by.htp.jd2_les01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AppConnectionPool {
    public static void main(String[] args) throws SQLException {

        ResourceBundle resource = ResourceBundle.getBundle("database");
        String dbUrl = resource.getString("dbUrl");
        String dbUser = resource.getString("dbUser");
        String dbPassword = resource.getString("dbPassword");

        ConnectionPool pool = new ConnectionPool(dbUrl, dbUser, dbPassword, 2);
        Connection conn = null;

        try {
            conn = pool.getConnection();
            try (Statement statement = conn.createStatement()) {
                    ResultSet res = statement.executeQuery("select * from shops");
                    System.out.println("QQ");
                    while (res.next()) {
                        System.out.println(res.getString(1) + " ");
                        System.out.println(res.getString(2) + " ");
                    }
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(conn!=null){
                try{
                    pool.returnConnection(conn);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }
    }
