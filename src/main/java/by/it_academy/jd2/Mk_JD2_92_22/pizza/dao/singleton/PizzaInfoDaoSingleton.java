package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.PizzaInfoDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IPizzaInfoDao;

public class PizzaInfoDaoSingleton {

    private IPizzaInfoDao pizzaInfoDao;
    private volatile static PizzaInfoDaoSingleton instance;

    public PizzaInfoDaoSingleton() {
        try {
            this.pizzaInfoDao = new PizzaInfoDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IPizzaInfoDao getInstance() {
        if (instance == null) {
            synchronized (PizzaInfoDaoSingleton.class) {
                if (instance == null) {
                    instance = new PizzaInfoDaoSingleton();
                }
            }
        }
        return instance.pizzaInfoDao;
    }
}
