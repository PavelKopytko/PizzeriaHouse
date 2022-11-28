package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.OrderDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.SelectedItemDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.TicketDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Order;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.SelectedItem;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ISelectedItem;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderFullDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuRowService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderFullService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.ITicketService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.MenuRowServiceSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.OrderServiceSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.TicketServiceSingleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderFullService implements IOrderFullService {

    private final IOrderFullDao orderFullDao;
    private final IOrderService orderService = OrderServiceSingleton.getInstance();
    private final ITicketService ticketService = TicketServiceSingleton.getInstance();
    private final IMenuRowService menuRowService = MenuRowServiceSingleton.getInstance();

    public OrderFullService(IOrderFullDao orderFullDao) {
        this.orderFullDao = orderFullDao;
    }

    @Override
    public ITicket create(OrderDto item) throws ServiceException, ValidateException, NotUniqServiceException {
        ITicket ticket;
        IOrder order;

        try {
            Order orderIn = new Order();
            orderIn.setDtCreate(LocalDateTime.now());
            orderIn.setDtUpdate(LocalDateTime.now());

            List<ISelectedItem> selectedItems = new ArrayList<>();
            for (SelectedItemDto selectedItemDto : item.getItems()) {
                ISelectedItem selectedItem = new SelectedItem(LocalDateTime.now(),
                        LocalDateTime.now(),
                        this.menuRowService.read(selectedItemDto.getMenuRowDto().getId()),
                        selectedItemDto.getCount()
                );
                selectedItems.add(selectedItem);
            }
            orderIn.setSelected(selectedItems);
            order = this.orderFullDao.create(orderIn);

            item.setId(order.getId());

            TicketDto ticketDto = new TicketDto();
            ticketDto.setOrder(item);

            ticketDto.setNumber("Number from OrderService");

            ticket = this.ticketService.create(ticketDto);

        } catch (IllegalStateException | IllegalArgumentException | IDServiceException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }
        return ticket;
    }

    @Override
    public IOrder read(long id) throws ServiceException, IDServiceException {
        IOrder order;
        try {
            order = orderFullDao.read(id);
            if (order == null) {
                throw new IDServiceException("Меню не найдено");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return order;
    }

    @Override
    public List<IOrder> get() throws ServiceException {

        List<IOrder> orders;
        try {
            orders = orderFullDao.get();
            if (orders.isEmpty()) {
                throw new IllegalArgumentException("Нет доступных заказов");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws ServiceException {
        try {
            this.orderFullDao.delete(id, dtUpdate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


}
