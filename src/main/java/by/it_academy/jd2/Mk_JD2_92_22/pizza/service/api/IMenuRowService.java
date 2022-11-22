package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;

public interface IMenuRowService {

    IMenuRow create(MenuRowDto item) throws ServiceException, ValidateException, NotUniqServiceException, IDServiceException;

    IMenuRow read(long id) throws IDServiceException, ServiceException;

    List<IMenuRow> get() throws ServiceException;

    IMenuRow update(long id, LocalDateTime dtUpdate, MenuRowDto item) throws ValidateException, ServiceException, NotUniqServiceException, IDServiceException;

    void delete(long id, LocalDateTime dtUpdate) throws ServiceException;
}
