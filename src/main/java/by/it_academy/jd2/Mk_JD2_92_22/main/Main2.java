package by.it_academy.jd2.Mk_JD2_92_22.main;

import by.it_academy.jd2.Mk_JD2_92_22.main.entity.Employee;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        DataSource dataSource = null;
        try {
            dataSource = DataSourceCreator.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10_000; i++) {
            try (Connection conn = dataSource.getConnection();
                 Statement stm = conn.createStatement()){

                try(ResultSet rs = stm.executeQuery("SELECT id, name, job, dep\n" +
                        "FROM structure.emp;");){
                    List<Employee> employees = mapList(rs);
                }

                try(ResultSet rs = stm.executeQuery("SELECT id, name, job, dep\n" +
                        "FROM structure.emp LIMIT 1;");){
                    Employee emp = null;
                    while (rs.next()){
                        emp = map(rs);
                    }
                }

            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        long stop = System.currentTimeMillis();
        System.out.println(stop - start);

    }

    public static Employee map(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setId(rs.getLong("id"));
        emp.setName(rs.getString("name"));
        long depRaw = rs.getLong("dep");
        if(!rs.wasNull()){
            emp.setDep(depRaw);
        }
        long jobRaw = rs.getLong("job");
        if(!rs.wasNull()){
            emp.setJob(jobRaw);
        }

        return emp;
    }

    public static List<Employee> mapList(ResultSet rs) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (rs.next()){
            employees.add(map(rs));
        }
        return employees;
    }
}
