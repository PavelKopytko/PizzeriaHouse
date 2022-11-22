package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.OrderFullDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderFullDao;

public class OrderFullDaoSingleton {

    private IOrderFullDao storage;
    private volatile static OrderFullDaoSingleton instance;

    public OrderFullDaoSingleton() {
        try {
            this.storage = new OrderFullDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IOrderFullDao getInstance() {
        if (instance == null) {
            synchronized (OrderFullDaoSingleton.class) {
                if (instance == null) {
                    instance = new OrderFullDaoSingleton();
                }
            }
        }
        return instance.storage;
    }
}
