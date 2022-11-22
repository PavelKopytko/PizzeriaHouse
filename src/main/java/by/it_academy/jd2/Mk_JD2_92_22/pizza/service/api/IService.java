package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;

public interface IService<TYPE, DtoType> {

    TYPE create(DtoType item) throws ServiceException, ValidateException, NotUniqServiceException;

    TYPE read(long id) throws IDServiceException, ServiceException;

    List<TYPE> get() throws ServiceException;

    TYPE update(long id, LocalDateTime dtUpdate, DtoType item) throws ValidateException, ServiceException, NotUniqServiceException;

    void delete(long id, LocalDateTime dtUpdate) throws ServiceException;
}
