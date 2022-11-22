package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.OrderDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderDao;

public class OrderDaoSingleton {

    private IOrderDao orderDao;
    private volatile static OrderDaoSingleton instance;

    public OrderDaoSingleton() {
        try {
            this.orderDao = new OrderDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IOrderDao getInstance() {
        if (instance == null) {
            synchronized (OrderDaoSingleton.class) {
                if (instance == null) {
                    instance = new OrderDaoSingleton();
                }
            }
        }
        return instance.orderDao;
    }
}
