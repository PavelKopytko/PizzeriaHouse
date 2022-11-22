package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.PizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PizzaInfoFromResultSetMapper {

    public static IPizzaInfo mapper(ResultSet rs) throws SQLException {
        return new PizzaInfo(
                rs.getLong("pi_id"),
                rs.getObject("pi_dt_create", LocalDateTime.class),
                rs.getObject("pi_dt_update", LocalDateTime.class),
                rs.getString("pi_name"),
                rs.getString("pi_descr"),
                rs.getLong("pi_size")
        );
    }

}
