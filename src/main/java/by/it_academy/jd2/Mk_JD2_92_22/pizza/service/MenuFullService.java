package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.MenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuFullDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuFullMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.PizzaInfoMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuFullService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IPizzaInfoService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MenuFullService implements IMenuFullService {

    private final IMenuFullDao menuDao;
    private final IPizzaInfoService pizzaInfoService;
    private final PizzaInfoMapper pizzaInfoMapper = new PizzaInfoMapper();
    private final MenuFullMapper menuFullMapper;


    public MenuFullService(IMenuFullDao menuDao, IPizzaInfoService pizzaInfoService, MenuFullMapper menuFullMapper) {
        this.menuDao = menuDao;
        this.pizzaInfoService = pizzaInfoService;
        this.menuFullMapper = menuFullMapper;
    }

    @Override
    public IMenu create(MenuDto item) throws ServiceException, ValidateException, NotUniqServiceException {
        MenuDto menuDto;
        IMenu menuOut;

        try {
            validate(item);
            List<IMenuRow> menuRows = new ArrayList<>();
            for (MenuRowDto row : item.getItems()) {
                IPizzaInfo pizzaInfo = pizzaInfoService.read(row.getInfoId());
                menuRows.add(new MenuRow(
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                pizzaInfo,
                                row.getPrice()
                        )
                );
            }
            IMenu menu = new Menu(
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    item.getName(),
                    item.isEnable(),
                    menuRows
            );
            menuOut = menuDao.create(menu);

//            menuDto = this.menuMapper.mapperDto(menu);

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        } catch (IDServiceException e) {
            throw new RuntimeException(e);
        }

        return menuOut;
    }

    @Override
    public IMenu read(long id) throws IDServiceException, ServiceException {

        IMenu menu;
        try {
            menu = menuDao.read(id);

            if (menu == null) {
                throw new IDServiceException("Меню не найдено");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return menu;
    }

    @Override
    public List<IMenu> get() throws ServiceException {
        List<IMenu> menus;
        try {
            menus = menuDao.get();
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

            List<IMenuRow> menuRows = new ArrayList<>();
            for (MenuRowDto row : item.getItems()) {

                IPizzaInfo pizzaInfo = pizzaInfoService.read(row.getInfoId());

                menuRows.add(new MenuRow(
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                pizzaInfo,
                                row.getPrice()
                        )
                );
            }

            readed.setDtUpdate(LocalDateTime.now());
            readed.setName(item.getName());
            readed.setEnable(item.isEnable());
            readed.setItems(menuRows);


            menu = menuDao.update(id, dtUpdate, readed);


        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        } catch (IDServiceException e) {
            throw new RuntimeException(e);
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

    private void validate(MenuDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали меню");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Вы не заполнили название меню");
        }
        if (item.getItems() == null || item.getItems().isEmpty()) {
            throw new IllegalArgumentException("Вы не заполнили строки меню");
        }
    }
}
