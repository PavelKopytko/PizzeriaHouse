package by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.StageDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Stage;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IStage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class StageMapper {

    public static IStage mapper(ResultSet rs) throws SQLException {
        return new Stage(
                rs.getLong("s_id"),
                rs.getObject("s_dt_create", LocalDateTime.class),
                rs.getObject("s_dt_update", LocalDateTime.class),
                rs.getString("s_description")
        );
    }

    public static StageDto mapperDto(IStage item) {
        return new StageDto(
                item.getId(),
                item.getDtUpdate().toEpochSecond(ZoneOffset.UTC),
                item.getDescription()
        );
    }

    public static IStage mapperDtoToEntity(StageDto item){
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Stage(localDateTime, localDateTime,item.getDescription());
    }

}
