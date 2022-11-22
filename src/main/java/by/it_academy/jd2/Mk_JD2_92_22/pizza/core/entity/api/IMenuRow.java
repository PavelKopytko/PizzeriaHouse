package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api;

import java.time.LocalDateTime;

/**
 * Строчка меню
 */
public interface IMenuRow {
    /**
     * Информация о пицце
     *
     * @return
     */
    IPizzaInfo getInfo();

    /**
     * Стоимость пиццы
     *
     * @return
     */
    double getPrice();

    /**
     * Меню доступно к использованию для заказа?
     *
     * @return
     */
    //boolean isEnable();

    long getId();

    void setId(long id);

    void setPrice(double price);

    void setInfo(IPizzaInfo info);

    LocalDateTime getDtUpdate();

    public void setDtUpdate(LocalDateTime dtUpdate);

    LocalDateTime getDtCreate();

    void setDtCreate(LocalDateTime dtCreate);

    IMenu getMenu();

    void setMenu(IMenu menu);
}
