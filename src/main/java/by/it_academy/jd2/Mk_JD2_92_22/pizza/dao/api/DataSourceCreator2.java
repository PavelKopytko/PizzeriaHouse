package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

public class DataSourceCreator2 {
    private static DataSourceCreator2 instance;
    private ComboPooledDataSource cpds;

    private DataSourceCreator2() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.postgresql.Driver");
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/pizzeria");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);
    }

    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (instance == null) {
            synchronized (DataSourceCreator2.class) {
                instance = new DataSourceCreator2();
            }
        }
        return instance.cpds;
    }
}
