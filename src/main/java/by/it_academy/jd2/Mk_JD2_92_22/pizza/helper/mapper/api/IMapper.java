package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.api;

public interface IMapper<TYPE, DTO> {
    DTO mapperDto(TYPE item);

    TYPE mapper(DTO item);



}
