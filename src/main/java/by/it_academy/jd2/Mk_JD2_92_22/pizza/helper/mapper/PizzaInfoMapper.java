package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.PizzaInfoDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.PizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PizzaInfoMapper {

    public PizzaInfoDto mapperDto(IPizzaInfo item) {
        return new PizzaInfoDto(
                item.getId(),
                item.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                item.getName(),
                item.getDescription(),
                item.getSize()
        );
    }

    public IPizzaInfo mapper(PizzaInfoDto item) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new PizzaInfo(localDateTime, localDateTime, item.getName(), item.getDescription(), item.getSize());
    }

    public IPizzaInfo mapperToEntity(PizzaInfoDto item){
        return new PizzaInfo(
                item.getId(),
                Instant.ofEpochMilli(item.getDtUpdate()).atZone(ZoneOffset.UTC).toLocalDateTime(),
                item.getName(),
                item.getDescription(),
                item.getSize()
        );
    }
}
