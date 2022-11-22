package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.OrderDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.OrderService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderService;

public class OrderServiceSingleton {

    private IOrderService orderService;
    private volatile static OrderServiceSingleton instance;

    public OrderServiceSingleton() {
        this.orderService = new OrderService(OrderDaoSingleton.getInstance());
    }

    public static IOrderService getInstance() {
        if (instance == null) {
            synchronized (OrderServiceSingleton.class) {
                if (instance == null) {
                    instance = new OrderServiceSingleton();
                }
            }
        }
        return instance.orderService;
    }
}
