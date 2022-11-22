package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuRowDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuRowMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.PizzaInfoMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuRowService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IPizzaInfoService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuRowService implements IMenuRowService {

    private IMenuRowDao menuRowDao;
    private IPizzaInfoService pizzaInfoService;
    private IMenuService menuService;
    private MenuRowMapper menuRowMapper;
    private PizzaInfoMapper pizzaInfoMapper = new PizzaInfoMapper();//
    private MenuMapper menuMapper = new MenuMapper();// get из MenuRowMapper

    public MenuRowService(IMenuRowDao menuRowDao, IPizzaInfoService pizzaInfoService, IMenuService menuService, MenuRowMapper menuRowMapper) {
        this.menuRowDao = menuRowDao;
        this.pizzaInfoService = pizzaInfoService;
        this.menuService = menuService;
        this.menuRowMapper = menuRowMapper;
    }

    @Override
    public IMenuRow create(MenuRowDto item) throws ValidateException, ServiceException, NotUniqServiceException, IDServiceException {
        MenuRowDto itemDto;
        IMenuRow menuRow;

        try {
            validate(item);

            IPizzaInfo pizzaInfoDto = pizzaInfoService.read(item.getInfoId());
            IMenu menuDto = menuService.read(item.getMenuId());

            menuRow = menuRowDao.create(menuRowMapper.mapper(item, pizzaInfoDto, menuDto));

//            itemDto = this.menuRowMapper.mapperDto(menuRow);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }

        return menuRow;
    }

    @Override
    public IMenuRow read(long id) throws IDServiceException, ServiceException {
        IMenuRow menuRow;

        try {
            menuRow = menuRowDao.read(id);
            if (menuRow == null) {
                throw new IDServiceException("Информация не найдена");
            }
//            menuRowDto = menuRowMapper.mapperDto(menuRow);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return menuRow;

    }

    @Override
    public List<IMenuRow> get() throws ServiceException {

        List<MenuRowDto> menuRowDtos = new ArrayList<>();
        List<IMenuRow> menuRows;
        try {
            menuRows = menuRowDao.get();
            if (menuRows.isEmpty()) {
//            if (!menuRows.isEmpty()) {
//                for (IMenuRow menuRow : menuRows) {
//                    menuRowDtos.add(menuRowMapper.mapperDto(menuRow));
//                }
//            } else {
                throw new IllegalArgumentException("Нет доступных menuRow");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return menuRows;
    }

    @Override
    public IMenuRow update(long id, LocalDateTime dtUpdate, MenuRowDto item) throws ValidateException, ServiceException, NotUniqServiceException, IDServiceException {

        IMenuRow readed;
//        IMenuRow menuRow;
        MenuRowDto menuRowDto;
        IMenuRow menuRow;
        try {
            validate(item);

            readed = menuRowDao.read(id);
            if (readed == null) {
                throw new IllegalArgumentException("Строка меню не найдена");
            }
            if (!readed.getDtUpdate().isEqual(dtUpdate)) {
                throw new IllegalArgumentException("К сожалению инфо уже было отредактировано кем-то другим");
            }

            IPizzaInfo pizzaInfo = pizzaInfoService.read(item.getInfoId());
            IMenu menu = menuService.read(item.getMenuId());

            readed.setDtUpdate(LocalDateTime.now());
            readed.setInfo(pizzaInfo);
            readed.setPrice(item.getPrice());
            readed.setMenu(menu);

            menuRow = menuRowDao.update(id, dtUpdate, readed);
//            menuRowDto = menuRowMapper.mapperDto(menuRow);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }

        return menuRow;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws ServiceException {

        try {
            menuRowDao.delete(id, dtUpdate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    public void validate(MenuRowDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали строку меню");
        }
        if (item.getInfoId() <= 0) {
            throw new IllegalArgumentException("Вы не заполнили инфо");
        }
        if (item.getPrice() <= 0) {
            throw new IllegalArgumentException("Вы не заполнили цену");
        }
        if (item.getMenuId() <= 0) {
            throw new IllegalArgumentException("Вы не указали меню");
        }
//        List<IMenuRow> menuRows = null;

//        try {
//            menuRows = menuRowDao.get();
//        } catch (DaoException e) {
//            throw new RuntimeException(e);
//        }
//
//        int count = 0;
//
//        for (IMenuRow menuRow : menuRows) {
//            if (item.getInfoId() == menuRow.getInfo().getId()) {
//                count++;
//            }
//        }
//        if (count == 0) {
//            throw new IllegalArgumentException("Вы указали неверный номер инфы");
//        }
    }
}
