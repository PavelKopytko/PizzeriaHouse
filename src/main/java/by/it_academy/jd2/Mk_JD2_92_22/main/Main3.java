package by.it_academy.jd2.Mk_JD2_92_22.main;

import by.it_academy.jd2.Mk_JD2_92_22.main.entity.Employee;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main3 {

    private static final String UNIQ_ERROR_CODE = "23505";
    private static final String USER_NAME_UNIQ_NAME = "user_name_uniq";
    private static final String USER_MAIL_UNIQ_NAME = "user_mail_uniq";

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

        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()){

            int updated = stm.executeUpdate(
                    "INSERT INTO test.users(name, mail)\n" +
                            "\tVALUES ('Ilya', 'gigabait_93@mail.ru');");
        } catch (SQLException e){
            if(UNIQ_ERROR_CODE.equals(e.getSQLState())){
                if(e.getMessage().contains(USER_NAME_UNIQ_NAME)){
                    System.out.println("Ошибка, проблемы с именем пользователя");
                } else if(e.getMessage().contains(USER_MAIL_UNIQ_NAME)){
                    System.out.println("Ошибка, проблемы с почтой пользователя");
                }
            }
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
