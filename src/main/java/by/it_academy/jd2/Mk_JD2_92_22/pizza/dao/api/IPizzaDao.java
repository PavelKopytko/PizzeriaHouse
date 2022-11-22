package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizza;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;

public interface IPizzaDao {

    IPizza create(IPizza item) throws DaoException;

    IPizza read(long id);

    List<IPizza> get();

    void delete(long id, LocalDateTime dtUpdate);
}
