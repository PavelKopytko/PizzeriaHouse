package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.PizzaInfoDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IPizzaInfoDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.PizzaInfoMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IPizzaInfoService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;

public class PizzaInfoService implements IPizzaInfoService {

    private IPizzaInfoDao pizzaInfoDao;
    private PizzaInfoMapper pizzaInfoMapper;

    public PizzaInfoService(IPizzaInfoDao pizzaInfoDao, PizzaInfoMapper pizzaInfoMapper) {
        this.pizzaInfoDao = pizzaInfoDao;
        this.pizzaInfoMapper = pizzaInfoMapper;
    }

    @Override
    public IPizzaInfo create(PizzaInfoDto item) throws ServiceException, ValidateException, NotUniqServiceException {

        PizzaInfoDto pizzaInfoDto;
        IPizzaInfo pizzaInfo;

        try {
            validate(item);
            pizzaInfo = pizzaInfoDao.create(pizzaInfoMapper.mapper(item));

//            pizzaInfoDto = this.pizzaInfoMapper.mapperDto(pizzaInfo);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }

        return pizzaInfo;
    }

    @Override
    public IPizzaInfo read(long id) throws IDServiceException, ServiceException {
        IPizzaInfo pizzaInfo;
        PizzaInfoDto pizzaInfoDto;

        try {
            pizzaInfo = pizzaInfoDao.read(id);

            if (pizzaInfo == null) {
                throw new IDServiceException("Информация не найдена");
            }
//            pizzaInfoDto = this.pizzaInfoMapper.mapperDto(pizzaInfo);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return pizzaInfo;
    }

    @Override
    public List<IPizzaInfo> get() throws ServiceException {

//        List<PizzaInfoDto> pizzaInfoDtos = new ArrayList<>();
        List<IPizzaInfo> pizzaInfo;
        try {
            pizzaInfo = pizzaInfoDao.get();
            if (pizzaInfo.isEmpty()) {
//            List<IPizzaInfo> pizzaInfos = pizzaInfoDao.get();
//            if (!pizzaInfos.isEmpty()) {
//                for (IPizzaInfo pizza : pizzaInfos) {
//                    pizzaInfoDtos.add(pizzaInfoMapper.mapperDto(pizza));
//                }
//            } else {
                throw new IllegalArgumentException("Нет доступных pizzaInfo");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return pizzaInfo;
    }

    @Override
    public IPizzaInfo update(long id, LocalDateTime dtUpdate, PizzaInfoDto item) throws ValidateException, ServiceException, NotUniqServiceException {

        IPizzaInfo readed;
        IPizzaInfo pizzaInfo;
        PizzaInfoDto pizzaInfoDto;
        try {
            validate(item);

            readed = pizzaInfoDao.read(id);
            if (readed == null) {
                throw new IllegalArgumentException("Инфо не найдено");
            }
            if (!readed.getDtUpdate().isEqual(dtUpdate)) {
                throw new IllegalArgumentException("К сожалению инфо уже было отредактировано кем-то другим");
            }

            readed.setDtUpdate(LocalDateTime.now());
            readed.setName(item.getName());
            readed.setDescription(item.getDescription());
            readed.setSize(item.getSize());

            pizzaInfo = pizzaInfoDao.update(id, dtUpdate, readed);
//            pizzaInfoDto = pizzaInfoMapper.mapperDto(pizzaInfo);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }

        return pizzaInfo;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws ServiceException {

        try {
            pizzaInfoDao.delete(id, dtUpdate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    public void validate(PizzaInfoDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали инфо");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Вы не заполнили название пиццы");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new IllegalArgumentException("Вы не заполнили описание аиццы");
        }
        if (item.getSize() <= 0) {
            throw new IllegalArgumentException("Вы не заполнили размер пиццы");
        }
    }
}
