package common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {
    private Context initalContext;

    //	Singleton：限定只能建構唯一實例
    private static ServiceLocator serviceLocator = new ServiceLocator();

    public static ServiceLocator getInstance() {
        return serviceLocator;
    }

    private ServiceLocator() {
        try {
            this.initalContext = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
// Singleton

    public DataSource getDataSource() {
        DataSource dataSource = null;
        try {
            Context ctx = (Context) this.initalContext.lookup("java:comp/env");
            dataSource = (DataSource) ctx.lookup("jdbc/lazy");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public DataSource getDataSource(String dataSourceName) {
        DataSource datasource = null;
        try {
            Context ctx = (Context) this.initalContext.lookup("java:comp/env");
            datasource = (DataSource) ctx.lookup(dataSourceName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return datasource;
    }
}