package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api;

import java.time.LocalDateTime;

/**
 * Квиток выдаваемый к заказу
 */
public interface ITicket {

    /**
     * Уникальный номер заказа
     *
     * @return
     */
    String getNumber();

    /**
     * Когда заказ получен
     *
     * @return
     */
    LocalDateTime getDtCreate();

    /**
     * Заказ для которого выдали квиток
     *
     * @return
     */

    long getId();

    IOrder getOrder();

    LocalDateTime getDtUpdate();
}
