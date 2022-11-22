package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api;

import java.time.LocalDateTime;

/**
 * Приготовленная пицца
 */
public interface IPizza {
    /**
     * Название пиццы
     * @return
     */
    String getName();

    /**
     * Размер пиццы
     * @return
     */
    int getSize();

    LocalDateTime getDtCreate();

    LocalDateTime getDtUpdate();

    IDoneOrder getOrder();
}
