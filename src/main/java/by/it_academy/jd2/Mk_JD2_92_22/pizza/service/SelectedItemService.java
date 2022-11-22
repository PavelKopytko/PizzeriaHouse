package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.SelectedItemDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.*;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.PizzaDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.ISelectedItemService;

import java.util.List;

public class SelectedItemService implements ISelectedItemService<SelectedItemDto> {

    private ISelectedItemDao selectedItemDao;
    private IMenuRowDao menuRowDao;
    private IOrderDao orderDao;
    private ITicketDao ticketDao;

    private IPizzaDao pizzaDao = PizzaDaoSingleton.getInstance();

    public SelectedItemService(ISelectedItemDao selectedItemDao, IMenuRowDao menuRowDao, IOrderDao orderDao, ITicketDao ticketDao) {

        this.selectedItemDao = selectedItemDao;
        this.menuRowDao = menuRowDao;
        this.orderDao = orderDao;
        this.ticketDao = ticketDao;
    }

    @Override
    public SelectedItemDto create(SelectedItemDto item) {
//        try {
//            validate(item);
//
//            IMenuRow menuRow = null;
//            IOrder order;
//
//            List<IMenuRow> menuRows = menuRowDao.get();
//
//            for (IMenuRow menuLine : menuRows) {
//                if (item.getMenuRow() == menuLine.getId()) {
//                    menuRow = menuLine;
//                }
//            }
//            if (menuRow == null) {
//                throw new RuntimeException("Передали неверное значение строки меню");
//            }
//            order = orderDao.create(OrderMapper.mapperToEntity());
//            ITicket ticket = ticketDao.create(TicketMapper.mapperToEntity(order));
//
//
//            ISelectedItem selectedItem = selectedItemDao.create(SelectedItemMapper.mapperDtoToEntity(item, menuRow, order));
//
//
//            return SelectedItemMapper.mapperDto(selectedItem);
//
//        } catch (IllegalStateException | IllegalArgumentException | DaoException e) {
//            throw new ServiceException(e);
//        }
        return null;
    }

    @Override
    public SelectedItemDto read(long id) {

//        ISelectedItem selectedItem = selectedItemDao.read(id);

        return null;//SelectedItemMapper.mapperDto(selectedItem);
    }

    @Override
    public List<SelectedItemDto> get() {

//        List<SelectedItemDto> selectedItemDtos = new ArrayList<>();
//
//        for (ISelectedItem selectedItem : selectedItemDao.get()) {
//            selectedItemDtos.add(SelectedItemMapper.mapperDto(selectedItem));
//        }

        return null;//selectedItemDtos;
    }

    @Override
    public SelectedItemDto update(long id, long dtUpdate, SelectedItemDto item) {
//        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(dtUpdate, 0, ZoneOffset.UTC);
//
//        ISelectedItem readed = selectedItemDao.read(id);
//
//        if (readed == null) {
//            throw new IllegalArgumentException("Инфо не найдено");
//        }
//        if (readed.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != dtUpdate) {
//            throw new IllegalArgumentException("К сожалению инфо уже было отредактировано кем-то другим");
//        }
        //readed.setDtUpdate(LocalDateTime.now());
        //readed.setName(item.getName());
        //readed.setDescription(item.getDescription());
        //readed.setSize(item.getSize());
//
        //selectedItemDao.update(id, localDateTime, readed);

        return null; //mapperDto(readed);
    }

    @Override
    public void delete(long id, long dtUpdate) {

//        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(dtUpdate, 0, ZoneOffset.UTC);
//
//        ISelectedItem readed = selectedItemDao.read(id);
//
//        ITicket ticket = null;
//        for (ITicket t : ticketDao.get()) {
//            if (t.getOrder().getId() == readed.getOrder().getId()) {
//                ticket = t;
//            }
//        }
//        // EXCEPTION if ticket=null;
//        ticketDao.delete(ticket.getId(), ticket.getDtUpdate());
//        selectedItemDao.delete(id, localDateTime);
//        orderDao.delete(readed.getOrder().getId(), readed.getOrder().getDtUpdate());


    }

    public void validate(SelectedItemDto item) {
//        if (item == null) {
//            throw new IllegalStateException("Вы не передали инфо");
//        }
//        if (item.getMenuRow() <= 0) {
//            throw new IllegalArgumentException("Вы не выбрали строку меню");
//        }
//        if (item.getCount() <= 0 || item.getCount() > 10) {
//            throw new IllegalArgumentException("Вы не заполнили количество пиццы");
//        }
    }
}
