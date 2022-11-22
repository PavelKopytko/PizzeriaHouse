package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusMapper {

    public static IOrderStatus mapper(ResultSet rs) throws SQLException {
        //return new OrderStatus(
        //        rs.getLong("os_id"),
        //        rs.getObject("os_dt_create", LocalDateTime.class),
        //        rs.getObject("os_dt_update", LocalDateTime.class),
        //        TicketMapper.mapper(rs),
        //        rs.getBoolean("done"),
        //        StageMapper.mapper(rs) // LIST
        //);
        return null;
    }
}
