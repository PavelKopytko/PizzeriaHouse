package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.PizzaInfoDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.MenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MenuRowMapper {

    private PizzaInfoMapper pizzaInfoMapper;
    private MenuMapper menuMapper;

    public MenuRowMapper(PizzaInfoMapper pizzaInfoMapper) {
        this.pizzaInfoMapper = pizzaInfoMapper;
        this.menuMapper = new MenuMapper();
    }

    public MenuRowMapper(PizzaInfoMapper pizzaInfoMapper, MenuMapper menuMapper) {
        this.pizzaInfoMapper = pizzaInfoMapper;
        this.menuMapper = menuMapper;
    }

    public PizzaInfoMapper getPizzaInfoMapper() {
        return pizzaInfoMapper;
    }

    public MenuMapper getMenuMapper() {
        return menuMapper;
    }

    public MenuRowDto mapperDto(IMenuRow item) {
        PizzaInfoDto pizzaInfo = new PizzaInfoDto(
                item.getInfo().getId(),
                item.getInfo().getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                item.getInfo().getName(),
                item.getInfo().getDescription(),
                item.getInfo().getSize()
        );
        MenuDto menu = new MenuDto(
                item.getMenu().getId(),
                item.getMenu().getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                item.getMenu().getName(),
                item.getMenu().isEnable()
        );

        return new MenuRowDto(
                item.getId(),
                item.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                pizzaInfo,
                item.getPrice(),
                menu
        );
    }

//    public static IMenuRow mapper(ResultSet rs) throws SQLException {
//
//        return new MenuRow(
//                rs.getLong("menu_row_id"),
//                rs.getObject("mr_dt_create", LocalDateTime.class),
//                rs.getObject("mr_dt_update", LocalDateTime.class),
//                PizzaInfoFromResultSetMapper.mapper(rs),
//                rs.getDouble("price"),
//                MenuFromResultSetMapper.mapper(rs)
//        );


//    public static MenuRowDto mapperDto(IMenuRow item) {
//        return new MenuRowDto(
//                item.getId(),
//                item.getDtUpdate().toEpochSecond(ZoneOffset.UTC),
//                item.getInfo().getId(),
//                item.getPrice(),
//                item.getMenu().getId()
//        );
//    }

//    public IMenuRow mapper(MenuRowDto item) {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        PizzaInfo pizzaInfo = new PizzaInfo();
//        pizzaInfo.setId(item.getInfoId());
//        Menu menu = new Menu();
//        menu.setId(item.getMenuId());
//
//        return new MenuRow(
//                localDateTime,
//                localDateTime,
//                pizzaInfo,
//                item.getPrice(),
//                menu
//        );
//    }

    public IMenuRow mapper(MenuRowDto item, IPizzaInfo pizzaInfo, IMenu menu) {
        LocalDateTime localDateTime = LocalDateTime.now();
//        PizzaInfo pizzaInfo = new PizzaInfo();
//        pizzaInfo.setId(item.getInfoId());
//        Menu menu = new Menu();
//        menu.setId(item.getMenuId());

        return new MenuRow(
                localDateTime,
                localDateTime,
                pizzaInfo,
                item.getPrice(),
                menu
        );
    }

}
