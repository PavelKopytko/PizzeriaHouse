package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.OrderDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderFullService /*extends IService<IOrder, OrderDto>*/ {

    ITicket create(OrderDto item) throws ServiceException, ValidateException, NotUniqServiceException;

    IOrder read(long id) throws IDServiceException, ServiceException;

    List<IOrder> get() throws ServiceException;

    void delete(long id, LocalDateTime dtUpdate) throws ServiceException;

}
