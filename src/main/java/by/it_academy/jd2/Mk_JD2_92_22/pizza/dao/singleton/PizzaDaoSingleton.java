package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.PizzaDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IPizzaDao;

public class PizzaDaoSingleton {

    private IPizzaDao pizzaDao;
    private volatile static PizzaDaoSingleton instance;

    public PizzaDaoSingleton() {
        try {
            this.pizzaDao = new PizzaDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IPizzaDao getInstance() {
        if (instance == null) {
            synchronized (PizzaDaoSingleton.class) {
                if (instance == null) {
                    instance = new PizzaDaoSingleton();
                }
            }
        }
        return instance.pizzaDao;
    }
}
