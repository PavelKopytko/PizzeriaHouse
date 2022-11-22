package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MenuService implements IMenuService {

    private final IMenuDao menuDao;
    private final MenuMapper menuMapper;

    public MenuService(IMenuDao menuDao, MenuMapper menuMapper) {
        this.menuDao = menuDao;
        this.menuMapper = menuMapper;
    }

    @Override
    public IMenu create(MenuDto item) throws ServiceException, ValidateException, NotUniqServiceException {
        IMenu menu;

        try {
            validate(item);
            menu = menuDao.create(menuMapper.mapper(item));

//            menuDto = this.menuMapper.mapperDto(menu);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }

        return menu;
    }

    @Override
    public IMenu read(long id) throws ServiceException, IDServiceException {
        IMenu menu;
        MenuDto menuDto;

        try {
            menu = menuDao.read(id);

            if (menu == null) {
                throw new IDServiceException("Меню не найдено");
            }
//            menuDto = menuMapper.mapperDto(menu);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return menu;
    }

    @Override
    public List<IMenu> get() throws ServiceException {
        List<MenuDto> menuDtos = new ArrayList<>();

        List<IMenu> menus;
        try {
            menus = menuDao.get();
//            if (!menus.isEmpty()) {
//                for (IMenu menu : menus) {
//                    menuDtos.add(menuMapper.mapperDto(menu));
//                }
//            } else {
            if (menus.isEmpty()) {
                throw new IllegalArgumentException("Нет доступных меню");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return menus;
    }

    @Override
    public IMenu update(long id, LocalDateTime dtUpdate, MenuDto item) throws ServiceException, ValidateException, NotUniqServiceException {

        IMenu readed;
        IMenu menu;
        MenuDto menuDto;
        try {
            validate(item);

            readed = menuDao.read(id);
            if (readed == null) {
                throw new IllegalArgumentException("Меню не найдено");
            }
            if (!readed.getDtUpdate().isEqual(dtUpdate)) {
                throw new IllegalArgumentException("К сожалению меню уже было отредактировано кем-то другим");
            }

            readed.setDtUpdate(LocalDateTime.now());
            readed.setName(item.getName());
            readed.setEnable(item.isEnable());

            menu = menuDao.update(id, dtUpdate, readed);
//            menuDaoto = menuMapper.mapperDto(menu);


        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }

        return menu;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws ServiceException {

        try {
            this.menuDao.delete(id, dtUpdate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    public void validate(MenuDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали меню");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Вы не заполнили название меню");
        }
    }
}
