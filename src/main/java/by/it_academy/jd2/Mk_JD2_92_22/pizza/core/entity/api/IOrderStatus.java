package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api;

import java.util.List;

/**
 * Статус заказа выданный по определённому квитку
 */
public interface IOrderStatus {

    /**
     * По какому квитку мы получили статус
     * @return
     */
    ITicket getTicket();

    /**
     * Получить список пройденных этапов
     * @return пройденные этапы заказа
     */
    List<IStage> getHistory();

    /**
     * Признак готовности заказа
     * @return true - готов, false - неготов
     */
    boolean isDone();
}
