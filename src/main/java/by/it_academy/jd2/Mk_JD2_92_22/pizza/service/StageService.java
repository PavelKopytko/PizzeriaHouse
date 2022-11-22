package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.StageDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IStage;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IStageDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.StageMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IStageService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class StageService implements IStageService<StageDto> {

    private IStageDao stageDao;

    public StageService(IStageDao stageDao) {
        this.stageDao = stageDao;
    }

    @Override
    public StageDto create(StageDto item) {
        try {
            validate(item);

            IStage stage = (IStage) stageDao.create(StageMapper.mapperDtoToEntity(item));

            return StageMapper.mapperDto(stage);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StageDto read(long id) {

        IStage stage = null;
        try {
            stage = (IStage) stageDao.read(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return StageMapper.mapperDto(stage);
    }

    @Override
    public List<StageDto> get() {

        List<StageDto> stageDto = new ArrayList<>();

//        try {
//            for (Object stage : stageDao.get()) {
//                //stageDto.add(StageMapper.mapperDto(stage));
//            }
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }

        return stageDto;
    }

    @Override
    public StageDto update(long id, long dtUpdate, StageDto item) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(dtUpdate, 0, ZoneOffset.UTC);

        IStage readed = (IStage) stageDao.read(id);

        if (readed == null) {
            throw new IllegalArgumentException("Инфо не найдено");
        }
        if (readed.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != dtUpdate) {
            throw new IllegalArgumentException("К сожалению инфо уже было отредактировано кем-то другим");
        }
        readed.setDtUpdate(LocalDateTime.now());
        readed.setDescription(item.getDescription());

        stageDao.update(id, localDateTime, readed);

        return StageMapper.mapperDto(readed);
    }

    @Override
    public void delete(long id, long dtUpdate) {

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(dtUpdate, 0, ZoneOffset.UTC);

        stageDao.delete(id, localDateTime);

    }

    public void validate(StageDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали stage");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new IllegalArgumentException("Вы не заполнили описание stage");
        }
    }
}
