package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Описание этапа выполнения заказа
 */
public interface IStage {
    /**
     * Описание этапа
     *
     * @return
     */
    String getDescription();

    /**
     * Когда этап был начат
     *
     * @return
     */
    LocalDateTime getDtUpdate();

    long getId();

    LocalDateTime getDtCreate();

    void setDtUpdate(LocalDateTime dtUpdate);
    void setDescription(String description);
}
