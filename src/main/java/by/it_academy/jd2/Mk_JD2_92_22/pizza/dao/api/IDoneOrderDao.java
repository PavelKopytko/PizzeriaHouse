package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IDoneOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;

public interface IDoneOrderDao {

    IDoneOrder create(IDoneOrder item) throws DaoException;

    IDoneOrder read(long id) throws DaoException;

    List<IDoneOrder> get() throws DaoException;

    void delete(long id, LocalDateTime dtUpdate) throws DaoException;

    boolean isExist(long id) throws DaoException;
}
