package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.OrderDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Order;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;


public class OrderService implements IOrderService {

    private final IOrderDao orderDao;

    public OrderService(IOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public IOrder create(OrderDto item) throws ServiceException, ValidateException, NotUniqServiceException {
        IOrder order;

        try {
            order = orderDao.create(
                    new Order(
                            LocalDateTime.now(),
                            LocalDateTime.now()
                    )
            );
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }
        return order;
    }

    @Override
    public IOrder read(long id) throws ServiceException, IDServiceException {
        IOrder order;
        try {
            order = orderDao.read(id);
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
            orders = orderDao.get();
            if (orders.isEmpty()) {
                throw new IllegalArgumentException("Нет доступных заказов");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public IOrder update(long id, LocalDateTime dtUpdate, OrderDto item) throws ServiceException, ValidateException, NotUniqServiceException {

        return null;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws ServiceException {
        try {
            this.orderDao.delete(id, dtUpdate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
