package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MenuMapper {

    public MenuDto mapperDto(IMenu item) {
        return new MenuDto(
                item.getId(),
                item.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                item.getName(),
                item.isEnable()
        );

    }

    public IMenu mapper(MenuDto menuDto) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Menu(localDateTime, localDateTime, menuDto.getName(), menuDto.isEnable());
    }

    public IMenu mapperToEntity(MenuDto item) {
        return new Menu(
                item.getId(),
                Instant.ofEpochMilli(item.getDtUpdate()).atZone(ZoneOffset.UTC).toLocalDateTime(),
                item.getName(),
                item.isEnable()
        );
    }
}
