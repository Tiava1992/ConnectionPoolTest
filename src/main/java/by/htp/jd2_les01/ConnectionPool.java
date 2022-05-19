package by.htp.jd2_les01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {
    private String databaseUrl;
    private String username;
    private String password;

    private int maxPoolSize=10;
    private int connNUm=0;

    private static final String SQL_VERIFYCONN="select 1";

    Stack<Connection> freepool=new Stack<Connection>();
    Set<Connection> ocuppiedPool=new HashSet<Connection>();

    public ConnectionPool(String databaseUrl, String username, String password, int maxPoolSize) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection conn=null;
        if(isFull()){
            throw new SQLException("The connection Pool is Fool");
        }
        conn=getConnectionFromPool();
        if(conn == null){
            conn=creatNewConnectionForPool();
        }
        conn=makeAvailable(conn);
        return conn;
    }

    protected synchronized void returnConnection(Connection conn) throws SQLException {
        if(conn==null){
            throw new NullPointerException();
        }
        if(!ocuppiedPool.remove(conn)){
            throw new SQLException("The connection is return");
        }
        freepool.push(conn);
    }

    private synchronized boolean isFull(){
        return ((freepool.size()==0)&&(connNUm>=maxPoolSize));
    }

    private Connection creatNewConnectionForPool() throws SQLException {
        Connection conn=creatNewConnection();
        connNUm++;
        ocuppiedPool.add(conn);
        return conn;
    }

    private Connection creatNewConnection() throws SQLException {
        Connection conn=null;
        conn= DriverManager.getConnection(databaseUrl, username, password);
        return conn;
    }

    private Connection getConnectionFromPool(){
        Connection conn=null;
        if(freepool.size()>0){
            conn=freepool.pop();
            ocuppiedPool.add(conn);
        }
        return conn;
    }

    private Connection makeAvailable (Connection conn) throws SQLException {
        if(isConnectionAvailable(conn)){
            return conn;
        }
        ocuppiedPool.remove(conn);
        connNUm--;
        conn.close();

        conn=creatNewConnection();
        ocuppiedPool.add(conn);
        connNUm++;
        return conn;
    }

    private boolean isConnectionAvailable(Connection conn){
        try(Statement st=conn.createStatement()) {
            st.executeQuery(SQL_VERIFYCONN);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}