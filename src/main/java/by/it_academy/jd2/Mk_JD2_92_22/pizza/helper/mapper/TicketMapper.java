package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Ticket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TicketMapper {
    public static ITicket mapper(ResultSet rs) throws SQLException {
        return new Ticket(
                rs.getLong("tc_id"),
                rs.getObject("tc_dt_create", LocalDateTime.class),
                rs.getObject("tc_dt_update", LocalDateTime.class),
                rs.getString("number"),
                OrderMapper.mapper(rs)
        );
    }

    public static ITicket mapperToEntity(IOrder order) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Ticket(localDateTime, localDateTime, "ticketNumber", order);
    }
}
