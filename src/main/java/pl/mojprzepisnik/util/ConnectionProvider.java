package pl.mojprzepisnik.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
    
    private static DataSource dataSource;
    
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/heroku_c2b0cd0e659bc0b?reconnect=true&user=bb4af7e1235509&password=332944e2");//getDataSource().getConnection();
    }
    
    public static DataSource getDataSource(){
        
        if (dataSource==null){
            try{
                Context initialContext = new InitialContext();
                Context envContext = (Context) initialContext.lookup("java:comp/env");
                DataSource ds = (DataSource)envContext.lookup("jdbc/my-recipe-basket");
                dataSource=ds;
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }
        return dataSource;
    }
}
