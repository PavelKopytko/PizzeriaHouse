package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.DoneOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IDoneOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DoneOrderMapper {

    public static IDoneOrder mapper(ResultSet rs) throws SQLException {
        return new DoneOrder(
                rs.getLong("don_id"),
                rs.getObject("don_dt_create", LocalDateTime.class),
                rs.getObject("don_dt_update", LocalDateTime.class),
                TicketMapper.mapper(rs)
        );
    }
}
