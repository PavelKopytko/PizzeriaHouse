package by.it_academy.jd2.Mk_JD2_92_22.main;

import by.it_academy.jd2.Mk_JD2_92_22.main.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main1 {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


            try (Connection conn =
                         DriverManager.getConnection(
                                 "jdbc:postgresql://localhost:5432/company",
                                 "postgres",
                                 "postgres");
                 Statement stm = conn.createStatement()){
                long start = System.currentTimeMillis();

                for (int i = 0; i < 10_000; i++) {
                    try(ResultSet rs = stm.executeQuery("SELECT id, name, job, dep\n" +
                            "FROM structure.emp;");){
                        List<Employee> employees = mapList(rs);

//                        System.out.println(employees);
                    }

                    try(ResultSet rs = stm.executeQuery("SELECT id, name, job, dep\n" +
                            "FROM structure.emp LIMIT 1;");){
                        Employee emp = null;
                        while (rs.next()){
                            emp = map(rs);
                        }

//                        System.out.println(emp);
                    }
                }

                long stop = System.currentTimeMillis();
                System.out.println(stop - start);
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }

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
