package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.SelectedItemDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.SelectedItem;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ISelectedItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SelectedItemMapper {
    public static ISelectedItem mapper(ResultSet rs) throws SQLException {
//        return new SelectedItem(
//                rs.getLong("si_id"),
//                rs.getObject("si_dt_create", LocalDateTime.class),
//                rs.getObject("si_dt_update", LocalDateTime.class),
//                MenuRowMapper.mapper(rs),
//                rs.getInt("count"),
//                OrderMapper.mapper(rs)
//        );
        return null;
    }

    public static SelectedItemDto mapperDto(ISelectedItem item) {
        return new SelectedItemDto(
//                item.getId(),
//                item.getDtUpdate().toEpochSecond(ZoneOffset.UTC),
//                item.getMenuRow().getId(),
//                item.getCount(),
//                item.getOrder().getId()
        );
    }

    public static ISelectedItem mapperDtoToEntity(SelectedItemDto item, IMenuRow menuRow, IOrder order) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new SelectedItem(localDateTime, localDateTime, menuRow, item.getCount(), order);
    }
}
