package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MenuFromResultSetMapper {

    public static IMenu mapper(ResultSet rs) throws SQLException {
        return new Menu(
                rs.getLong("m_id"),
                rs.getObject("m_dt_create", LocalDateTime.class),
                rs.getObject("m_dt_update", LocalDateTime.class),
                rs.getString("m_name"),
                rs.getBoolean("m_enable")
        );
    }
}
