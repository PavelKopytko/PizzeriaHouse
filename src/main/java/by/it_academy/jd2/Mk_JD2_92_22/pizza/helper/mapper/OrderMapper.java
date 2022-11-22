package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Order;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderMapper {

//    public IOrder mapper(OrderDto orderDto) {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        List<>
//        for ()
//        return new Order(localDateTime, localDateTime);
//    }
    public static IOrder mapper(ResultSet rs) throws SQLException {
        return new Order(
                rs.getLong("o_id"),
                rs.getObject("o_dt_create", LocalDateTime.class),
                rs.getObject("o_dt_update", LocalDateTime.class)
        );
    }

    public static IOrder mapperToEntity() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Order(localDateTime, localDateTime);
    }
}
