package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.OrderFullDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.OrderFullService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderFullService;

public class OrderFullServiceSingleton {

    private IOrderFullService orderService;
    private volatile static OrderFullServiceSingleton instance;

    public OrderFullServiceSingleton() {
        this.orderService = new OrderFullService(OrderFullDaoSingleton.getInstance());
    }

    public static IOrderFullService getInstance() {
        if (instance == null) {
            synchronized (OrderFullServiceSingleton.class) {
                if (instance == null) {
                    instance = new OrderFullServiceSingleton();
                }
            }
        }
        return instance.orderService;
    }
}
