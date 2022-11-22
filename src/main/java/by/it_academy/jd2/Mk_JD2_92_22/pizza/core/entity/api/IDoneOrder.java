package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Готовый заказ
 */
public interface IDoneOrder {

    /**
     * Квиток по которому заказ готовился
     * @return квиток выданный при формировании заказа
     */
    ITicket getTicket();

    /**
     * Готовые пиццы приготовленные по заказу
     * @return список пицц
     */
    List<IPizza> getItems();

    long getId();
    LocalDateTime getDtCreate();
    LocalDateTime getDtUpdate();
}
