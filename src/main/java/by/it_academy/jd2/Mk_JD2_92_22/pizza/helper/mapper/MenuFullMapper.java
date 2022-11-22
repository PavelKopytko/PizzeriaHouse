package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.MenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.PizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class MenuFullMapper {

    public MenuDto mapperDto(IMenu item){
        return new MenuDto(
                item.getId(),
                item.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                item.getName(),
                item.isEnable()
        );

    }

    public IMenu mapper (MenuDto item){
        LocalDateTime localDateTime = LocalDateTime.now();
        List<IMenuRow> menuRows = new ArrayList<>();
        for (MenuRowDto menuRow : item.getItems()){
            PizzaInfo pizzaInfo = new PizzaInfo(
                    menuRow.getInfo().getId(),
                    localDateTime,
                    localDateTime,
                    menuRow.getInfo().getName(),
                    menuRow.getInfo().getDescription(),
                    menuRow.getInfo().getSize()
            );

            IMenuRow row = new MenuRow(
                    menuRow.getId(),
                    localDateTime,
                    localDateTime,
                    pizzaInfo,
                    menuRow.getPrice()
            );
            menuRows.add(row);
        }
        return new Menu(
                item.getId(),
                Instant.ofEpochMilli(item.getDtUpdate()).atZone(ZoneOffset.UTC).toLocalDateTime(),
                item.getName(),
                item.isEnable()
        );
    }
}
