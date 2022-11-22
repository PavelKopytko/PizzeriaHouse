package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Pizza;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IDoneOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PizzaMapper {
    public static IPizza mapper(ResultSet rs) throws SQLException {
        return new Pizza(
                rs.getLong("p_id"),
                rs.getObject("p_dt_create", LocalDateTime.class),
                rs.getObject("p_dt_update", LocalDateTime.class),
                rs.getString("name"),
                rs.getInt("size"),
                DoneOrderMapper.mapper(rs)
        );
    }

    public static IPizza mapperFromMenuRow(IMenuRow menuRow, IDoneOrder order){
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Pizza(
                localDateTime,
                localDateTime,
                menuRow.getInfo().getName(),
                (int) menuRow.getInfo().getSize(), // переделать на инт в PizzaInfo
                order);
    }

}
