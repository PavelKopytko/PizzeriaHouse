package by.it_academy.jd2.Mk_JD2_92_22.pizza.service;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.TicketDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Ticket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.ITicketDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.ITicketService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;


public class TicketService implements ITicketService {

    private final ITicketDao ticketDao;
    private final IOrderService orderService;

    public TicketService(ITicketDao ticketDao, IOrderService orderService) {
        this.ticketDao = ticketDao;
        this.orderService = orderService;
    }

    @Override
    public ITicket create(TicketDto item) throws ServiceException, ValidateException, NotUniqServiceException {
        ITicket ticket;

        try {
            IOrder order = orderService.create(item.getOrder());
            ticket = ticketDao.create(
                    new Ticket(
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            item.getNumber(),
                            order
                    )
            );

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ValidateException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotUniqDaoException e) {
            throw new NotUniqServiceException(e.getMessage(), e);
        }
        return ticket;
    }

    @Override
    public ITicket read(long id) throws ServiceException, IDServiceException {
        ITicket ticket;
        try {
            ticket = ticketDao.read(id);
            if (ticket == null) {
                throw new IDServiceException("Меню не найдено");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return ticket;
    }

    @Override
    public List<ITicket> get() throws ServiceException {

        List<ITicket> tickets;
        try {
            tickets = ticketDao.get();
            if (tickets.isEmpty()) {
                throw new IllegalArgumentException("Нет доступных заказов");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tickets;
    }

    @Override
    public ITicket update(long id, LocalDateTime dtUpdate, TicketDto item) throws ServiceException, ValidateException, NotUniqServiceException {

        return null;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws ServiceException {
        try {
            this.ticketDao.delete(id, dtUpdate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
